package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class ServiceHelper {

	private ServiceHelper() {
		super();
	}
	
	public static boolean exist(Class<? extends IBean> type, String id) {
		if (!id.matches("\\d+")) {
			return false;
		}
		
		return DaoFactory.createDao(type).exist(Integer.parseInt(id));
	}
}