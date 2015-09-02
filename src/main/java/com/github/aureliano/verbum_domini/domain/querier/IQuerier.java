package com.github.aureliano.verbum_domini.domain.querier;

import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.domain.bean.IBean;

public interface IQuerier {

	public abstract <T extends IBean> Integer count(Class<T> type);
	
	public abstract Map<String, Object> get(IBean bean);
	
	public abstract <T extends IBean> List<Map<String, Object>> find(Class<T> type, int offset, int limit);
	
	public abstract <T extends IBean> List<Map<String, Object>> find(Class<T> type, String filterName, Object filterValue, int offset, int limit);
}