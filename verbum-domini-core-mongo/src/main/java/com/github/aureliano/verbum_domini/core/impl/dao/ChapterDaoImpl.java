package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.ChapterDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.validation.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class ChapterDaoImpl implements ChapterDao {

	private static final int MAX_ELEMENTS_BY_QUERY = Integer.parseInt(AppConfiguration.instance().getProperty("database.dao.max_elements_by_query"));
	
	private PersistenceManagerImpl persistenceManager;
	
	public ChapterDaoImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}
	
	@Override
	public ChapterBean get(Serializable id) {
		return DaoHelper.findOne(ChapterBeanImpl.class, id);
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
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<ChapterBean> list(ChapterBean filter, Integer firstResult, Integer maxResults) {
		MongoCollection<ChapterBeanImpl> coll = this.persistenceManager.fetchCollection(ChapterBeanImpl.class);
		Pagination<ChapterBean> pagination = new Pagination<ChapterBean>();
		
		pagination.setSize(this.countQueryResult(coll, this.createBasicFilter(filter)));
		FindIterable<ChapterBeanImpl> iterable = coll.find(this.createBasicFilter(filter))
				.skip(firstResult - 1)
				.limit(maxResults);
		
		Iterator<ChapterBeanImpl> iterator = iterable.iterator();
		List<ChapterBean> chapters = new ArrayList<>();
		while (iterator.hasNext()) {
			chapters.add(iterator.next());
		}
		
		pagination.setElements(chapters);
		
		return pagination;
	}

	@Override
	public ChapterBean load(Serializable id) {
		return this.get(id);
	}

	@Override
	public boolean exist(Serializable id) {
		ChapterBean bean = new ChapterBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(ChapterBean bean) {
		MongoCollection<ChapterBeanImpl> coll = this.persistenceManager.fetchCollection(ChapterBeanImpl.class);
		
		List<String> messages = ValidationHelper.validate(bean, Save.class);
		if (!messages.isEmpty()) {
			throw new VerbumDominiException(messages.toString());
		}
		
		if (bean.getId() == null) {
			coll.insertOne((ChapterBeanImpl) bean);
		} else {
			coll.replaceOne(Filters.eq("_id", bean.getId()), (ChapterBeanImpl) bean);
		}
		
		return bean.getId();
	}

	@Override
	public void delete(ChapterBean bean) {
		MongoCollection<ChapterBeanImpl> coll = this.persistenceManager.fetchCollection(ChapterBeanImpl.class);
		coll.deleteOne(Filters.eq("_id", bean.getId()));
	}

	@Override
	public void deleteAll() {
		MongoCollection<ChapterBeanImpl> coll = this.persistenceManager.fetchCollection(ChapterBeanImpl.class);
		coll.drop();
	}
	
	private BasicDBObject createBasicFilter(ChapterBean filter) {
		BasicDBObject dbFilter = new BasicDBObject();
		
		if (filter == null) {
			return dbFilter;
		}
		
		if ((filter.getBook() != null) && (filter.getBook().getId() != null)) {
			dbFilter.append("book_id", filter.getBook().getId());
		}
		
		return dbFilter;
	}
	
	private int countQueryResult(MongoCollection<ChapterBeanImpl> coll, BasicDBObject dbFilter) {
		return (int) coll.count(dbFilter);
	}
}