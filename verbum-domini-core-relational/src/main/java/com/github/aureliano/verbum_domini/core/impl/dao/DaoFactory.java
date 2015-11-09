package com.github.aureliano.verbum_domini.core.impl.dao;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;

public final class DaoFactory {

	private DaoFactory() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IBean> IDao<T> createDao(Class<T> type) {
		if (VerseBean.class.isAssignableFrom(type)) {
			return (IDao<T>) new VerseDaoImpl();
		} else if (ChapterBean.class.isAssignableFrom(type)) {
			return (IDao<T>) new ChapterDaoImpl();
		} else if (BookBean.class.isAssignableFrom(type)) {
			return (IDao<T>) new BookDaoImpl();
		} else if (BibleBean.class.isAssignableFrom(type)) {
			return (IDao<T>) new BibleDaoImpl();
		} else if (UserBean.class.isAssignableFrom(type)) {
			return (IDao<T>) new UserDaoImpl();
		} else {
			throw new IllegalArgumentException("Unsupported DAO creation for bean type " + type.getName());
		}
	}
}