package com.github.aureliano.verbum_domini.dao;

import java.io.Serializable;
import java.util.List;

import com.github.aureliano.verbum_domini.web.ServiceParams;

public interface IDao<T> {

	public abstract List<Pagination<T>> list();
	
	public abstract List<Pagination<T>> list(T filter);
	
	public abstract List<Pagination<T>> list(T filter, ServiceParams params);
	
	public abstract T load(Serializable id);
}