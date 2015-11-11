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
import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.dao.UserDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class UserDaoImpl implements UserDao {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	private PersistenceManagerImpl persistenceManager;
	
	public UserDaoImpl() {
		this.persistenceManager = (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}

	@Override
	public void delete(UserBean bean) {
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
			session.createQuery("delete " + UserBeanImpl.class.getSimpleName()).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public boolean exist(Serializable id) {
		UserBean bean = new UserBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public UserBean get(Serializable id) {
		return DaoHelper.get(UserBeanImpl.class, id);
	}

	@Override
	public Pagination<UserBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<UserBean> list(UserBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<UserBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<UserBean> list(UserBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<UserBean> list(UserBean filter, Integer firstResult, Integer maxResults) {
		Pagination<UserBean> pagination = new Pagination<UserBean>();
		
		Session session = this.persistenceManager.openReadOnlySession();
		pagination.setSize(this.countCriteriaResult(this.createDefaultCriteria(session, filter)));
		
		List<UserBean> books = this.createDefaultCriteria(session, filter)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.list();
		pagination.setElements(books);
		
		session.close();
		return pagination;
	}

	@Override
	public UserBean load(Serializable id) {
		return DaoHelper.load(UserBeanImpl.class, id);
	}

	@Override
	public Serializable save(UserBean bean) {
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
	public boolean authenticate(String login, String password) {
		Session session = this.persistenceManager.openReadOnlySession();
		Criteria criteria = session.createCriteria(UserBeanImpl.class);
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		Object rowCount = criteria
			.add(Restrictions.eq("login", login))
			.add(Restrictions.eq("password", password))
			.setProjection(Projections.rowCount())
			.uniqueResult();
		
		return ((Number) rowCount).intValue() > 0;
	}
	
	private Criteria createDefaultCriteria(Session session, UserBean user) {
		Criteria criteria = session.createCriteria(UserBeanImpl.class);
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		if (user == null) {
			return criteria;
		}
		
		if (user.getLogin() != null) {
			criteria.add(Restrictions.eq("login", user.getLogin()));
		}
		
		return criteria;
	}
	
	private int countCriteriaResult(Criteria criteria) {
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}