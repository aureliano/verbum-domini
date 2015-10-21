package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.validation.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class AnnotationDaoImpl implements AnnotationDao {

	private static final int MAX_ELEMENTS_BY_QUERY = Integer.parseInt(AppConfiguration.instance().getProperty("database.dao.max_elements_by_query"));
	
	private PersistenceManagerImpl persistenceManager;
	
	public AnnotationDaoImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}

	@Override
	public AnnotationBean get(Serializable id) {
		return DaoHelper.findOne(AnnotationBeanImpl.class, id);
	}

	@Override
	public Pagination<AnnotationBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<AnnotationBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter, ServiceParams params) {
		return this.list(filter, params.getStart(), params.getPages() * MAX_ELEMENTS_BY_QUERY);
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter, Integer firstResult, Integer maxResults) {
		MongoCollection<AnnotationBeanImpl> coll = this.persistenceManager.fetchCollection(AnnotationBeanImpl.class);
		Pagination<AnnotationBean> pagination = new Pagination<AnnotationBean>();
		
		pagination.setSize(this.countQueryResult(coll, this.createBasicFilter(filter)));
		FindIterable<AnnotationBeanImpl> iterable = coll.find(this.createBasicFilter(filter))
				.skip(firstResult - 1)
				.limit(maxResults)
				.projection(Projections.include("_id", "number", "chapter_id"));
		
		Iterator<AnnotationBeanImpl> iterator = iterable.iterator();
		List<AnnotationBean> annotations = new ArrayList<>();
		while (iterator.hasNext()) {
			annotations.add(iterator.next());
		}
		
		pagination.setElements(annotations);
		
		return pagination;
	}

	@Override
	public AnnotationBean load(Serializable id) {
		return this.get(id);
	}

	@Override
	public boolean exist(Serializable id) {
		AnnotationBean bean = new AnnotationBeanImpl();
		bean.setId((Integer) id);
		
		return DaoHelper.exist(bean);
	}

	@Override
	public Serializable save(AnnotationBean bean) {
		MongoCollection<AnnotationBeanImpl> coll = this.persistenceManager.fetchCollection(AnnotationBeanImpl.class);

		List<String> messages = ValidationHelper.validate(bean, Save.class);
		if (!messages.isEmpty()) {
			throw new VerbumDominiException(messages.toString());
		}
		
		if (bean.getId() == null) {
			coll.insertOne((AnnotationBeanImpl) bean);
		} else {
			coll.replaceOne(Filters.eq("_id", bean.getId()), (AnnotationBeanImpl) bean);
		}
		
		return bean.getId();
	}

	@Override
	public void delete(AnnotationBean bean) {
		MongoCollection<AnnotationBeanImpl> coll = this.persistenceManager.fetchCollection(AnnotationBeanImpl.class);
		coll.deleteOne(Filters.eq("_id", bean.getId()));
	}

	@Override
	public void deleteAll() {
		MongoCollection<AnnotationBeanImpl> coll = this.persistenceManager.fetchCollection(AnnotationBeanImpl.class);
		coll.drop();
	}
	
	private BasicDBObject createBasicFilter(AnnotationBean filter) {
		BasicDBObject dbFilter = new BasicDBObject();
		
		if (filter == null) {
			return dbFilter;
		}
		
		if ((filter.getChapter() != null) && (filter.getChapter().getId() != null)) {
			dbFilter.append("chapter_id", filter.getChapter().getId());
		}
		
		return dbFilter;
	}
	
	private int countQueryResult(MongoCollection<AnnotationBeanImpl> coll, BasicDBObject dbFilter) {
		return (int) coll.count(dbFilter);
	}
}