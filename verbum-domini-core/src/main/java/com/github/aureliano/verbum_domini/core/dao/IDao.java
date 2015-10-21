package com.github.aureliano.verbum_domini.core.dao;

import java.io.Serializable;

import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public interface IDao<T extends IBean> {

	public abstract Pagination<T> list();
	
	public abstract Pagination<T> list(T filter);
	
	public abstract Pagination<T> list(ServiceParams params);
	
	public abstract Pagination<T> list(T filter, ServiceParams params);
	
	public abstract Pagination<T> list(T filter, Integer firstResult, Integer maxResults);
	
	public abstract T load(Serializable id);
	
	public abstract T get(Serializable id);
	
	public abstract boolean exist(Serializable id);
	
	public abstract Serializable save(T bean);
	
	public abstract void delete(T bean);
	
	public abstract void deleteAll();
}