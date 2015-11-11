package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class VerseDaoImpl implements VerseDao {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	private PersistenceManagerImpl persistenceManager;
	
	public VerseDaoImpl() {
		this.persistenceManager = (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}

	@Override
	public VerseBean get(Serializable id) {
		return DaoHelper.get(VerseBeanImpl.class, id);
	}

	@Override
	public Pagination<VerseBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<VerseBean> list(VerseBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<VerseBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<VerseBean> list(VerseBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<VerseBean> list(VerseBean filter, Integer firstResult, Integer maxResults) {
		Pagination<VerseBean> pagination = new Pagination<VerseBean>();
		
		Session session = this.persistenceManager.openReadOnlySession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<Object[]> data = this.createDefaultCriteria(session, filter)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.list();
		
		List<VerseBean> verses = new ArrayList<>(data.size());
		for (int i = 0; i < data.size(); i++) {
			Object[] row = data.get(i);
			VerseBean bean = new VerseBeanImpl();
			
			bean.setId((Integer) row[0]);
			bean.setNumber(row[1].toString());
			bean.setChapter((ChapterBean) row[2]);
			
			verses.add(bean);
		}
		
		pagination.setElements(verses);
		
		session.close();
		return pagination;
	}

	@Override
	public VerseBean load(Serializable id) {
		return DaoHelper.load(VerseBeanImpl.class, id);
	}

	@Override
	public boolean exist(Serializable id) {
		VerseBean bean = new VerseBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(VerseBean bean) {
		Session session = this.persistenceManager.openSession();
		Transaction transaction = session.beginTransaction();
		Integer id = bean.getId();
		
		try {
			if (bean.getId() == null) {
				Criteria criteria = this.createDefaultCriteria(session, null);
				id = this.countCriteriaResult(criteria) + 1;
				
				bean.setId(id);
				session.save(bean);
			} else {
				session.update(bean);
			}
			
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			throw new VerbumDominiException(ex);
		} finally {
			session.close();
		}
		
		return id;
	}

	@Override
	public void delete(VerseBean bean) {
		Session session = this.persistenceManager.openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			session.delete(bean);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			throw new VerbumDominiException(ex);
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteAll() {
		Session session = this.persistenceManager.openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			session.createQuery("delete " + VerseBeanImpl.class.getSimpleName()).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	private Criteria createDefaultCriteria(Session session, VerseBean verse) {
		Criteria criteria = session.createCriteria(VerseBeanImpl.class, "verse");
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("verse.id"))
				.add(Projections.property("verse.number"))
				.add(Projections.property("verse.chapter")));
		
		if (verse == null) {
			return criteria;
		}
		
		if (verse.getChapter() != null) {
			criteria.add(Restrictions.eq("chapter", verse.getChapter()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}