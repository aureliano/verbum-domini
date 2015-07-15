package com.github.aureliano.verbum_domini.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.bean.BibleBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class BibleDao implements IDao<BibleBean> {

	private PersistenceManager persistenceManager;
	
	public BibleDao() {
		this.persistenceManager = PersistenceManager.instance();
	}
	
	@Override
	public BibleBean load(Serializable id) {
		return (BibleBean) this.persistenceManager.openSession().load(BibleBean.class, id);
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
	public Pagination<BibleBean> list(BibleBean filter, ServiceParams params) {
		Pagination<BibleBean> pagination = new Pagination<BibleBean>();
		
		Session session = this.persistenceManager.openSession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<BibleBean> bibles = this.createDefaultCriteria(session, filter)
				.setFirstResult(params.getStart() - 1)
				.setMaxResults(params.getPages() * PAGE_ELEMENTS)
				.list();
		pagination.setElements(bibles);
		
		session.close();
		return pagination;
	}
	
	public BibleBean findByLanguage(String language) {
		BibleBean filter = new BibleBean();
		filter.setLanguage(language);
		
		Pagination<BibleBean> pagination = this.list(filter);
		if (pagination.getSize() > 1) {
			throw new RuntimeException("Found more than one Bible with language " + language);
		}
		
		return (pagination.getSize() > 0) ? pagination.getElements().get(0) : null;
	}
	
	private Criteria createDefaultCriteria(Session session, BibleBean bible) {
		Criteria criteria = session.createCriteria(BibleBean.class);
		
		if (bible == null) {
			return criteria;
		}
		
		if (bible.getLanguage() != null) {
			criteria.add(Restrictions.eq("language", bible.getLanguage()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}