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
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.dao.BibleDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class BibleDaoImpl implements BibleDao {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	private PersistenceManagerImpl persistenceManager;
	
	public BibleDaoImpl() {
		this.persistenceManager = (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}

	@Override
	public BibleBean get(Serializable id) {
		return DaoHelper.get(BibleBeanImpl.class, id);
	}

	@Override
	public Pagination<BibleBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<BibleBean> list(BibleBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<BibleBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<BibleBean> list(BibleBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<BibleBean> list(BibleBean filter, Integer firstResult, Integer maxResults) {
		Pagination<BibleBean> pagination = new Pagination<BibleBean>();
		
		Session session = this.persistenceManager.openReadOnlySession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<Object[]> data = this.createDefaultCriteria(session, filter)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.list();
		
		List<BibleBean> bibles = new ArrayList<>(data.size());
		for (int i = 0; i < data.size(); i++) {
			Object[] row = data.get(i);
			BibleBean bean = new BibleBeanImpl();
			
			bean.setId((Integer) row[0]);
			bean.setName(row[1].toString());
			bean.setLanguage(row[2].toString());
			
			bibles.add(bean);
		}
		
		pagination.setElements(bibles);
		
		session.close();
		return pagination;
	}

	@Override
	public BibleBean load(Serializable id) {
		return DaoHelper.load(BibleBeanImpl.class, id);
	}

	@Override
	public boolean exist(Serializable id) {
		BibleBean bean = new BibleBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(BibleBean bean) {
		Session session = this.persistenceManager.openSession();
		Transaction transaction = session.beginTransaction();
		Integer id = bean.getId();
		
		try {
			if (bean.getId() == null) {
				Criteria criteria = session.createCriteria(BibleBeanImpl.class).add(Restrictions.eq("name", bean.getName()));
				if (this.countCriteriaResult(criteria) > 0) {
					throw new VerbumDominiException("Duplicated bible with name " + bean.getName());
				}
				
				criteria = this.createDefaultCriteria(session, null);
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
	public void delete(BibleBean bean) {
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
			session.createQuery("delete " + BibleBeanImpl.class.getSimpleName()).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	private Criteria createDefaultCriteria(Session session, BibleBean bible) {
		Criteria criteria = session.createCriteria(BibleBeanImpl.class);
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("id"))
				.add(Projections.property("name"))
				.add(Projections.property("language")));
		
		if (bible == null) {
			return criteria;
		}
		
		if (bible.getLanguage() != null) {
			criteria.add(Restrictions.eq("language", bible.getLanguage().toLowerCase()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}