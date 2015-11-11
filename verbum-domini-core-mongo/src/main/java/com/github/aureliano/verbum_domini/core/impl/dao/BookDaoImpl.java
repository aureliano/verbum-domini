package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.dao.BookDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.validation.group.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class BookDaoImpl implements BookDao {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	private PersistenceManagerImpl persistenceManager;
	
	public BookDaoImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}

	@Override
	public BookBean get(Serializable id) {
		return DaoHelper.findOne(BookBeanImpl.class, id);
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
		MongoCollection<BookBeanImpl> coll = this.persistenceManager.fetchCollection(BookBeanImpl.class);
		Pagination<BookBean> pagination = new Pagination<BookBean>();
		
		pagination.setSize(this.countQueryResult(coll, this.createBasicFilter(filter)));
		FindIterable<BookBeanImpl> iterable = coll.find(this.createBasicFilter(filter))
				.skip(firstResult - 1)
				.limit(maxResults);
		
		Iterator<BookBeanImpl> iterator = iterable.iterator();
		List<BookBean> books = new ArrayList<>();
		while (iterator.hasNext()) {
			books.add(iterator.next());
		}
		
		pagination.setElements(books);
		
		return pagination;
	}

	@Override
	public BookBean load(Serializable id) {
		return this.get(id);
	}

	@Override
	public boolean exist(Serializable id) {
		BookBean bean = new BookBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(BookBean bean) {
		MongoCollection<BookBeanImpl> coll = this.persistenceManager.fetchCollection(BookBeanImpl.class);
		
		List<String> messages = ValidationHelper.validate(bean, Save.class);
		if (!messages.isEmpty()) {
			throw new VerbumDominiException(messages.toString());
		}
		
		if (bean.getId() == null) {
			BookBeanImpl entity = coll.find(new Document()
				.append("bible_id", bean.getBible().getId()).append("name", bean.getName())).first();
			if (entity != null) {
				throw new VerbumDominiException("Duplicated book with name " + entity.getName());
			}
			
			coll.insertOne((BookBeanImpl) bean);
		} else {
			coll.replaceOne(Filters.eq("_id", bean.getId()), (BookBeanImpl) bean);
		}
		
		return bean.getId();
	}

	@Override
	public void delete(BookBean bean) {
		MongoCollection<BookBeanImpl> coll = this.persistenceManager.fetchCollection(BookBeanImpl.class);
		coll.deleteOne(Filters.eq("_id", bean.getId()));
	}

	@Override
	public void deleteAll() {
		MongoCollection<BookBeanImpl> coll = this.persistenceManager.fetchCollection(BookBeanImpl.class);
		coll.drop();
	}
	
	private BasicDBObject createBasicFilter(BookBean filter) {
		BasicDBObject dbFilter = new BasicDBObject();
		
		if (filter == null) {
			return dbFilter;
		}
		
		if ((filter.getBible() != null) && (filter.getBible().getId() != null)) {
			dbFilter.append("bible_id", filter.getBible().getId());
		}
		
		return dbFilter;
	}
	
	private int countQueryResult(MongoCollection<BookBeanImpl> coll, BasicDBObject dbFilter) {
		return (int) coll.count(dbFilter);
	}
}