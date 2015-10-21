package com.github.aureliano.verbum_domini.core.impl.dao;

import java.io.Serializable;
import java.util.Iterator;

import org.bson.Document;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

public final class DaoHelper {

	private DaoHelper() {
		super();
	}
	
	public static <T extends IBean> T findOne(Class<T> entityType,
			Serializable id) {
		
		Iterable<T> iterable = getPersistenceManager().fetchCollection(entityType)
				.find(new BasicDBObject("_id", id));
		Iterator<T> iterator = iterable.iterator();
		
		return (iterator.hasNext()) ? iterator.next() : null;
	}
	
	public static int nextId(IBean bean) {
		MongoDatabase db = getPersistenceManager().fetchDatabase();
		
		String entity = bean.getClass().getSimpleName().replaceFirst("Bean(Impl)?$", "").toLowerCase();
		MongoCollection<Document> coll = db.getCollection(entity);
		int id = lastId(coll);
		
		return (id + 1);
	}
	
	@SuppressWarnings("unchecked")
	public static Integer lastId(MongoCollection<?> coll) {
		FindIterable<Document> it = (FindIterable<Document>) coll.find()
			.projection(Projections.include("_id")).sort(new BasicDBObject("_id", -1)).limit(1);
		Document res = it.first();
		
		if (res == null) {
			return 0;
		}
		
		return res.getInteger("_id");
	}
	
	public static PersistenceManagerImpl getPersistenceManager() {
		return (PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager();
	}
	
	public static <T extends IBean> boolean exist(IBean bean) {
		if ((bean == null) || (bean.getId() == null)) {
			return false;
		}
		
		MongoCollection<T> coll = (MongoCollection<T>) getPersistenceManager().fetchCollection(bean.getClass());
		long count = coll.count(new BasicDBObject("_id", bean.getId()));
		
		return count > 0;
	}
}