package com.github.aureliano.verbum_domini.db;

import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.AppConfiguration;
import com.github.aureliano.verbum_domini.domain.bean.IBean;
import com.github.aureliano.verbum_domini.domain.querier.IQuerier;
import com.github.aureliano.verbum_domini.domain.querier.SqlQuerier;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public class DatabaseFacade {

	private static DatabaseFacade instance;
	
	private IQuerier querier;
	
	public DatabaseFacade() {
		this.querier = this.createQuerier();
	}
	
	public <T extends IBean> Integer count(Class<T> type) {
		return this.querier.count(type);
	}
	
	public Map<String, Object> get(IBean bean) {
		return this.querier.get(bean);
	}

	public <T extends IBean> List<Map<String, Object>> find(Class<T> type, int offset, int limit) {
		return this.querier.find(type, offset, limit);
	}
	
	public <T extends IBean> List<Map<String, Object>> find(Class<T> type, String filterName,
			Object filterValue, int offset, int limit) {
		return this.querier.find(type, filterName, filterValue, offset, limit);
	}
	
	public static DatabaseFacade instance() {
		if (instance == null) {
			instance = new DatabaseFacade();
		}
		
		return instance;
	}
	
	private IQuerier createQuerier() {
		String supported = "[sql]";
		String dataStoreType = AppConfiguration.instance().getProperty("database.application.type");
		
		if (dataStoreType.equalsIgnoreCase("sql")) {
			return SqlQuerier.instance();
		} else {
			throw new VerbumDominiException(
				"Unsupported database application type: [" + dataStoreType + "]. Expected: " + supported);
		}
	}
}