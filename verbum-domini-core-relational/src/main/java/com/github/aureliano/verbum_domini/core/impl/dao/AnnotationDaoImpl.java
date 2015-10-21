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
import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class AnnotationDaoImpl implements AnnotationDao {

	private static final int MAX_ELEMENTS_BY_QUERY = Integer.parseInt(AppConfiguration.instance().getProperty("database.dao.max_elements_by_query"));
	
	private PersistenceManagerImpl persistenceManager;
	
	public AnnotationDaoImpl() {
		this.persistenceManager = (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}

	@Override
	public AnnotationBean get(Serializable id) {
		return DaoHelper.get(AnnotationBeanImpl.class, id);
	}

	@Override
	public Pagination<AnnotationBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<AnnotationBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter, Integer firstResult, Integer maxResults) {
		Pagination<AnnotationBean> pagination = new Pagination<AnnotationBean>();
		
		Session session = this.persistenceManager.openReadOnlySession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<Object[]> data = this.createDefaultCriteria(session, filter)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.list();
		
		List<AnnotationBean> annotations = new ArrayList<AnnotationBean>(data.size());
		for (int i = 0; i < data.size(); i++) {
			Object[] row = data.get(i);
			AnnotationBean bean = new AnnotationBeanImpl();
			
			bean.setId((Integer) row[0]);
			bean.setNumber(row[1].toString());
			bean.setChapter((ChapterBean) row[2]);
			
			annotations.add(bean);
		}
		
		pagination.setElements(annotations);
		
		session.close();
		return pagination;
	}

	@Override
	public AnnotationBean load(Serializable id) {
		return DaoHelper.load(AnnotationBeanImpl.class, id);
	}

	@Override
	public boolean exist(Serializable id) {
		AnnotationBean bean = new AnnotationBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(AnnotationBean bean) {
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
	public void delete(AnnotationBean bean) {
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
			session.createQuery("delete " + AnnotationBeanImpl.class.getSimpleName()).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	private Criteria createDefaultCriteria(Session session, AnnotationBean annotation) {
		Criteria criteria = session.createCriteria(AnnotationBeanImpl.class, "annotation");
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("annotation.id"))
				.add(Projections.property("annotation.number"))
				.add(Projections.property("annotation.chapter")));
		
		if (annotation == null) {
			return criteria;
		}
		
		if (annotation.getChapter() != null) {
			criteria.add(Restrictions.eq("chapter", annotation.getChapter()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}