package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.dao.UserDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.validation.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class UserDaoImpl implements UserDao {

	private static final int MAX_ELEMENTS_BY_QUERY = Integer.parseInt(AppConfiguration.instance().getProperty("database.dao.max_elements_by_query"));
	
	private PersistenceManagerImpl persistenceManager;
	
	public UserDaoImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}

	@Override
	public void delete(UserBean bean) {
		MongoCollection<UserBeanImpl> coll = this.persistenceManager.fetchCollection(UserBeanImpl.class);
		coll.deleteOne(Filters.eq("_id", bean.getId()));
	}

	@Override
	public void deleteAll() {
		MongoCollection<UserBeanImpl> coll = this.persistenceManager.fetchCollection(UserBeanImpl.class);
		coll.drop();
	}

	@Override
	public boolean exist(Serializable id) {
		UserBean bean = new UserBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public UserBean get(Serializable id) {
		return DaoHelper.findOne(UserBeanImpl.class, id);
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
		MongoCollection<UserBeanImpl> coll = this.persistenceManager.fetchCollection(UserBeanImpl.class);
		Pagination<UserBean> pagination = new Pagination<UserBean>();
		
		pagination.setSize(this.countQueryResult(coll, this.createBasicFilter(filter)));
		FindIterable<UserBeanImpl> iterable = coll.find(this.createBasicFilter(filter))
				.skip(firstResult - 1)
				.limit(maxResults);
		
		Iterator<UserBeanImpl> iterator = iterable.iterator();
		List<UserBean> users = new ArrayList<>();
		while (iterator.hasNext()) {
			users.add(iterator.next());
		}
		
		pagination.setElements(users);
		
		return pagination;
	}

	@Override
	public UserBean load(Serializable id) {
		return this.get(id);
	}

	@Override
	public Serializable save(UserBean bean) {
		MongoCollection<UserBeanImpl> coll = this.persistenceManager.fetchCollection(UserBeanImpl.class);
		
		List<String> messages = ValidationHelper.validate(bean, Save.class);
		if (!messages.isEmpty()) {
			throw new VerbumDominiException(messages.toString());
		}
		
		if (bean.getId() == null) {
			coll.insertOne((UserBeanImpl) bean);
		} else {
			coll.replaceOne(Filters.eq("_id", bean.getId()), (UserBeanImpl) bean);
		}
		
		return bean.getId();
	}

	@Override
	public boolean authenticate(String login, String password) {
		MongoCollection<UserBeanImpl> coll = this.persistenceManager.fetchCollection(UserBeanImpl.class);
		BasicDBObject dbFilter = new BasicDBObject();
		
		dbFilter.append("login", login).append("password", password);
		return coll.count(dbFilter) > 0;
	}
	
	private BasicDBObject createBasicFilter(UserBean filter) {
		BasicDBObject dbFilter = new BasicDBObject();
		
		if (filter == null) {
			return dbFilter;
		}
		
		if (filter.getLogin() != null) {
			dbFilter.append("login", filter.getLogin());
		}
		
		return dbFilter;
	}
	
	private int countQueryResult(MongoCollection<UserBeanImpl> coll, BasicDBObject dbFilter) {
		return (int) coll.count(dbFilter);
	}
}