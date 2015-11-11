package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.validation.group.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class VerseDaoImpl implements VerseDao {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	private PersistenceManagerImpl persistenceManager;
	
	public VerseDaoImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}

	@Override
	public VerseBean get(Serializable id) {
		return DaoHelper.findOne(VerseBeanImpl.class, id);
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
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<VerseBean> list(VerseBean filter, Integer firstResult, Integer maxResults) {
		MongoCollection<VerseBeanImpl> coll = this.persistenceManager.fetchCollection(VerseBeanImpl.class);
		Pagination<VerseBean> pagination = new Pagination<VerseBean>();
		
		pagination.setSize(this.countQueryResult(coll, this.createBasicFilter(filter)));
		FindIterable<VerseBeanImpl> iterable = coll.find(this.createBasicFilter(filter))
				.skip(firstResult - 1)
				.limit(maxResults)
				.projection(Projections.include("_id", "number", "chapter_id"));
		
		Iterator<VerseBeanImpl> iterator = iterable.iterator();
		List<VerseBean> verses = new ArrayList<>();
		while (iterator.hasNext()) {
			verses.add(iterator.next());
		}
		
		pagination.setElements(verses);
		
		return pagination;
	}

	@Override
	public VerseBean load(Serializable id) {
		return this.get(id);
	}

	@Override
	public boolean exist(Serializable id) {
		VerseBean bean = new VerseBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(VerseBean bean) {
		MongoCollection<VerseBeanImpl> coll = this.persistenceManager.fetchCollection(VerseBeanImpl.class);
		
		List<String> messages = ValidationHelper.validate(bean, Save.class);
		if (!messages.isEmpty()) {
			throw new VerbumDominiException(messages.toString());
		}
		
		if (bean.getId() == null) {
			coll.insertOne((VerseBeanImpl) bean);
		} else {
			coll.replaceOne(Filters.eq("_id", bean.getId()), (VerseBeanImpl) bean);
		}
		
		return bean.getId();
	}

	@Override
	public void delete(VerseBean bean) {
		MongoCollection<VerseBeanImpl> coll = this.persistenceManager.fetchCollection(VerseBeanImpl.class);
		coll.deleteOne(Filters.eq("_id", bean.getId()));
	}

	@Override
	public void deleteAll() {
		MongoCollection<VerseBeanImpl> coll = this.persistenceManager.fetchCollection(VerseBeanImpl.class);
		coll.drop();
	}
	
	private BasicDBObject createBasicFilter(VerseBean filter) {
		BasicDBObject dbFilter = new BasicDBObject();
		
		if (filter == null) {
			return dbFilter;
		}
		
		if ((filter.getChapter() != null) && (filter.getChapter().getId() != null)) {
			dbFilter.append("chapter_id", filter.getChapter().getId());
		}
		
		return dbFilter;
	}
	
	private int countQueryResult(MongoCollection<VerseBeanImpl> coll, BasicDBObject dbFilter) {
		return (int) coll.count(dbFilter);
	}
}