package com.github.aureliano.verbum_domini.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.bean.BookBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class BookDao implements IDao<BookBean> {

	private PersistenceManager persistenceManager;

	public BookDao() {
		this.persistenceManager = PersistenceManager.instance();
	}

	@Override
	public Pagination<BookBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<BookBean> list(BookBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<BookBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<BookBean> list(BookBean filter, ServiceParams params) {
		Pagination<BookBean> pagination = new Pagination<BookBean>();
		
		Session session = this.persistenceManager.openSession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<BookBean> books = this.createDefaultCriteria(session, filter)
				.setFirstResult(params.getStart() - 1)
				.setMaxResults(params.getPages() * PAGE_ELEMENTS)
				.list();
		pagination.setElements(books);
		
		session.close();
		return pagination;
	}

	@Override
	public BookBean load(Serializable id) {
		return (BookBean) this.persistenceManager.openSession().load(BookBean.class, id);
	}
	
	private Criteria createDefaultCriteria(Session session, BookBean book) {
		Criteria criteria = session.createCriteria(BookBean.class, "book");
		
		if (book == null) {
			return criteria;
		}
		
		if (book.getBible() != null) {
			criteria
				.createAlias("book.bible", "bible")
				.add(Restrictions.eq("bible.id", book.getBible().getId()));
			
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}