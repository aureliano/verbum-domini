package com.github.aureliano.verbum_domini.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.github.aureliano.verbum_domini.bean.BibleBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public class BibleDao implements IDao<BibleBean> {

	private PersistenceManager persistenceManager;
	
	public BibleDao() {
		this.persistenceManager = PersistenceManager.instance();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BibleBean> list() {
		Session session = this.persistenceManager.openSession();
		return session.createCriteria(BibleBean.class).list();
	}
	
	@Override
	public BibleBean load(Serializable id) {
		return (BibleBean) this.persistenceManager.openSession().load(BibleBean.class, id);
	}
	
	public BibleBean findByLanguage(String language) {
		Session session = this.persistenceManager.openSession();
		@SuppressWarnings("unchecked")
		List<BibleBean> result = session
			.createCriteria(BibleBean.class)
			.add(Restrictions.eq("language", language))
			.setMaxResults(1)
			.list();
		
		return (result.isEmpty()) ? null : result.get(0);
	}
}