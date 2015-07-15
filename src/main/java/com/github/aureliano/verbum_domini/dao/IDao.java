package com.github.aureliano.verbum_domini.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<T> {

	public List<T> list();
	
	public T load(Serializable id);
}