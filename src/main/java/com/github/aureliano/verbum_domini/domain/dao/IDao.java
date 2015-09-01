package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;

import com.github.aureliano.verbum_domini.web.ServiceParams;

public interface IDao<T> {

	public static final Integer MAX_ELEMENTS_BY_QUERY = 25;

	public abstract Pagination<T> list();
	
	public abstract Pagination<T> list(T filter);
	
	public abstract Pagination<T> list(ServiceParams params);
	
	public abstract Pagination<T> list(T filter, ServiceParams params);
	
	public abstract T load(Serializable id);
	
	public abstract T get(Serializable id);
}