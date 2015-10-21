package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.ChapterDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class ChapterDaoImpl implements ChapterDao {

	private static final int MAX_ELEMENTS_BY_QUERY = Integer.parseInt(AppConfiguration.instance().getProperty("database.dao.max_elements_by_query"));
	
	private PersistenceManagerImpl persistenceManager;
	
	public ChapterDaoImpl() {
		this.persistenceManager = (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}

	@Override
	public ChapterBean get(Serializable id) {
		return DaoHelper.get(ChapterBeanImpl.class, id);
	}

	@Override
	public Pagination<ChapterBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<ChapterBean> list(ChapterBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<ChapterBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<ChapterBean> list(ChapterBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<ChapterBean> list(ChapterBean filter, Integer firstResult, Integer maxResults) {
		Pagination<ChapterBean> pagination = new Pagination<ChapterBean>();
		
		Session session = this.persistenceManager.openReadOnlySession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<ChapterBean> chapters = this.createDefaultCriteria(session, filter)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.list();
		pagination.setElements(chapters);
		
		session.close();
		return pagination;
	}

	@Override
	public ChapterBean load(Serializable id) {
		return DaoHelper.load(ChapterBeanImpl.class, id);
	}

	@Override
	public boolean exist(Serializable id) {
		ChapterBean bean = new ChapterBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(ChapterBean bean) {
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
	public void delete(ChapterBean bean) {
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
			session.createQuery("delete " + ChapterBeanImpl.class.getSimpleName()).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	private Criteria createDefaultCriteria(Session session, ChapterBean chapter) {
		Criteria criteria = session.createCriteria(ChapterBeanImpl.class, "chapter");
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		if (chapter == null) {
			return criteria;
		}
		
		if (chapter.getBook() != null) {
			criteria.add(Restrictions.eq("book", chapter.getBook()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}