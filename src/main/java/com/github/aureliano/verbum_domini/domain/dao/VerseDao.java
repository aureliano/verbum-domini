package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.domain.bean.VerseBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class VerseDao implements IDao<VerseBean> {

	private PersistenceManager persistenceManager;
	
	public VerseDao() {
		this.persistenceManager = PersistenceManager.instance();
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
		Pagination<VerseBean> pagination = new Pagination<VerseBean>();
		
		Session session = this.persistenceManager.openSession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<VerseBean> books = this.createDefaultCriteria(session, filter)
				.setFirstResult(params.getStart() - 1)
				.setMaxResults(params.getPages() * MAX_ELEMENTS_BY_QUERY)
				.list();
		pagination.setElements(books);
		
		session.close();
		return pagination;
	}

	@Override
	public VerseBean load(Serializable id) {
		return (VerseBean) this.persistenceManager.openSession().load(VerseBean.class, id);
	}

	@Override
	public VerseBean get(Serializable id) {
		return (VerseBean) this.persistenceManager.openSession().get(VerseBean.class, id);
	}
	
	private Criteria createDefaultCriteria(Session session, VerseBean verse) {
		Criteria criteria = session.createCriteria(VerseBean.class, "verse");
		
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