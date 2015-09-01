package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.domain.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class AnnotationDao implements IDao<AnnotationBean> {

	private PersistenceManager persistenceManager;
	
	public AnnotationDao() {
		this.persistenceManager = PersistenceManager.instance();
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
		Pagination<AnnotationBean> pagination = new Pagination<AnnotationBean>();
		
		Session session = this.persistenceManager.openSession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<AnnotationBean> annotation = this.createDefaultCriteria(session, filter)
				.setFirstResult(params.getStart() - 1)
				.setMaxResults(params.getPages() * MAX_ELEMENTS_BY_QUERY)
				.list();
		pagination.setElements(annotation);
		
		session.close();
		return pagination;
	}

	@Override
	public AnnotationBean load(Serializable id) {
		return (AnnotationBean) this.persistenceManager.openSession().load(AnnotationBean.class, id);
	}

	@Override
	public AnnotationBean get(Serializable id) {
		return (AnnotationBean) this.persistenceManager.openSession().get(AnnotationBean.class, id);
	}
	
	private Criteria createDefaultCriteria(Session session, AnnotationBean annotation) {
		Criteria criteria = session.createCriteria(AnnotationBean.class, "annotation");
		
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