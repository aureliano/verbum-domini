package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;

public final class DaoHelper {

	private DaoHelper() {
		super();
	}
	
	public static <T extends IBean> T get(Class<T> entityType, Serializable id) {
		Session session = getPersistenceManager().openReadOnlySession();
		return (T) session.get(entityType, id);
	}
	
	public static <T extends IBean> T load(Class<T> entityType, Serializable id) {
		Session session = getPersistenceManager().openReadOnlySession();
		return (T) session.load(entityType, id);
	}
	
	public static PersistenceManagerImpl getPersistenceManager() {
		return (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}
	
	public static <T extends IBean> boolean exist(IBean bean) {
		if ((bean == null) || (bean.getId() == null)) {
			return false;
		}

		Session session = getPersistenceManager().openReadOnlySession();
		Criteria criteria = session.createCriteria(bean.getClass());
		criteria.setReadOnly(true);
		criteria.setFlushMode(FlushMode.MANUAL);
		
		criteria.add(Restrictions.eq("id", bean.getId()));
		
		int count = (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
		
		return count > 0;
	}
}