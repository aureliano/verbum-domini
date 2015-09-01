package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class ChapterDao implements IDao<ChapterBean> {
	
	private PersistenceManager persistenceManager;

	public ChapterDao() {
		this.persistenceManager = PersistenceManager.instance();
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
		Pagination<ChapterBean> pagination = new Pagination<ChapterBean>();
		
		Session session = this.persistenceManager.openSession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<ChapterBean> books = this.createDefaultCriteria(session, filter)
				.setFirstResult(params.getStart() - 1)
				.setMaxResults(params.getPages() * MAX_ELEMENTS_BY_QUERY)
				.list();
		pagination.setElements(books);
		
		session.close();
		return pagination;
	}

	@Override
	public ChapterBean load(Serializable id) {
		return (ChapterBean) this.persistenceManager.openSession().load(ChapterBean.class, id);
	}

	@Override
	public ChapterBean get(Serializable id) {
		return (ChapterBean) this.persistenceManager.openSession().get(ChapterBean.class, id);
	}
	
	private Criteria createDefaultCriteria(Session session, ChapterBean chapter) {
		Criteria criteria = session.createCriteria(ChapterBean.class, "chapter");
		
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