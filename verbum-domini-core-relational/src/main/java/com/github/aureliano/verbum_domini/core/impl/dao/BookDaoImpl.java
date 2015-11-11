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
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.dao.BookDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class BookDaoImpl implements BookDao {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	private PersistenceManagerImpl persistenceManager;
	
	public BookDaoImpl() {
		this.persistenceManager = (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}

	@Override
	public BookBean get(Serializable id) {
		return DaoHelper.get(BookBeanImpl.class, id);
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
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<BookBean> list(BookBean filter, Integer firstResult, Integer maxResults) {
		Pagination<BookBean> pagination = new Pagination<BookBean>();
		
		Session session = this.persistenceManager.openReadOnlySession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<BookBean> books = this.createDefaultCriteria(session, filter)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.list();
		pagination.setElements(books);
		
		session.close();
		return pagination;
	}

	@Override
	public BookBean load(Serializable id) {
		return DaoHelper.load(BookBeanImpl.class, id);
	}

	@Override
	public boolean exist(Serializable id) {
		BookBean bean = new BookBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(BookBean bean) {
		Session session = this.persistenceManager.openSession();
		Transaction transaction = session.beginTransaction();
		Integer id = bean.getId();
		
		try {
			if (bean.getId() == null) {
				Criteria criteria = session.createCriteria(BookBeanImpl.class)
						.add(Restrictions.eq("name", bean.getName()))
						.add(Restrictions.eq("bible", bean.getBible()));
				if (this.countCriteriaResult(criteria) > 0) {
					throw new VerbumDominiException("Duplicated book with name " + bean.getName());
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
	public void delete(BookBean bean) {
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
			session.createQuery("delete " + BookBeanImpl.class.getSimpleName()).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	private Criteria createDefaultCriteria(Session session, BookBean book) {
		Criteria criteria = session.createCriteria(BookBeanImpl.class, "book");
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		if (book == null) {
			return criteria;
		}
		
		if (book.getBible() != null) {
			criteria.add(Restrictions.eq("bible", book.getBible()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}