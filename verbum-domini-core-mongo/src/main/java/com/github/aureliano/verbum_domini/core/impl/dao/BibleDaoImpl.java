package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.dao.BibleDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.validation.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class BibleDaoImpl implements BibleDao {

	private static final int MAX_ELEMENTS_BY_QUERY = Integer.parseInt(AppConfiguration.instance().getProperty("database.dao.max_elements_by_query"));
	
	private PersistenceManagerImpl persistenceManager;
	
	public BibleDaoImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}

	@Override
	public BibleBean get(Serializable id) {
		return DaoHelper.findOne(BibleBeanImpl.class, id);
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
	public Pagination<BibleBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<BibleBean> list(BibleBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<BibleBean> list(BibleBean filter, Integer firstResult, Integer maxResults) {
		MongoCollection<BibleBeanImpl> coll = this.persistenceManager.fetchCollection(BibleBeanImpl.class);
		Pagination<BibleBean> pagination = new Pagination<BibleBean>();
		
		pagination.setSize(this.countQueryResult(coll, this.createBasicFilter(filter)));
		FindIterable<BibleBeanImpl> iterable = coll.find(this.createBasicFilter(filter))
				.skip(firstResult - 1)
				.limit(maxResults)
				.projection(Projections.include("_id", "name", "language"));
		
		Iterator<BibleBeanImpl> iterator = iterable.iterator();
		List<BibleBean> bibles = new ArrayList<>();
		while (iterator.hasNext()) {
			bibles.add(iterator.next());
		}
		
		pagination.setElements(bibles);
		
		return pagination;
	}

	@Override
	public BibleBean load(Serializable id) {
		return this.get(id);
	}

	@Override
	public boolean exist(Serializable id) {
		BibleBean bean = new BibleBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(BibleBean bean) {
		MongoCollection<BibleBeanImpl> coll = this.persistenceManager.fetchCollection(BibleBeanImpl.class);
		
		List<String> messages = ValidationHelper.validate(bean, Save.class);
		if (!messages.isEmpty()) {
			throw new VerbumDominiException(messages.toString());
		}
		
		if (bean.getId() == null) {
			BibleBeanImpl entity = coll.find(new Document("name", bean.getName())).first();
			if (entity != null) {
				throw new VerbumDominiException("Duplicated bible with name " + entity.getName());
			}
			
			coll.insertOne((BibleBeanImpl) bean);
		} else {
			coll.replaceOne(Filters.eq("_id", bean.getId()), (BibleBeanImpl) bean);
		}
		
		return bean.getId();
	}

	@Override
	public void delete(BibleBean bean) {
		MongoCollection<BibleBeanImpl> coll = this.persistenceManager.fetchCollection(BibleBeanImpl.class);
		coll.deleteOne(Filters.eq("_id", bean.getId()));
	}

	@Override
	public void deleteAll() {
		MongoCollection<BibleBeanImpl> coll = this.persistenceManager.fetchCollection(BibleBeanImpl.class);
		coll.drop();
	}
	
	private BasicDBObject createBasicFilter(BibleBean filter) {
		BasicDBObject dbFilter = new BasicDBObject();
		
		if (filter == null) {
			return dbFilter;
		}
		
		if (filter.getLanguage() != null) {
			dbFilter.append("language", filter.getLanguage());
		}
		
		return dbFilter;
	}
	
	private int countQueryResult(MongoCollection<BibleBeanImpl> coll, BasicDBObject dbFilter) {
		return (int) coll.count(dbFilter);
	}
}