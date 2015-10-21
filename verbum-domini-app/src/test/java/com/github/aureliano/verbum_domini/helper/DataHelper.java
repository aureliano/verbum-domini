package com.github.aureliano.verbum_domini.helper;

import java.util.Arrays;
import java.util.List;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class DataHelper {

	private static DataHelper instance;
	
	private boolean dataHelpersInitialized;
	
	private DataHelper() {
		this.dataHelpersInitialized = false;
	}
	
	public static DataHelper instance() {
		if (instance == null) {
			instance = new DataHelper();
		}
		
		return instance;
	}
	
	public void initializeDataHelpers() {
		if (this.dataHelpersInitialized) {
			return;
		}
		
		AppConfiguration.instance().getPersistenceManager().startUp();
		clearDatabase();
		
		BibleDataHelper.createBibles();
		BookDataHelper.createBooks();
		ChapterDataHelper.createChapters();
		VerseDataHelper.createVerses();
		AnnotationDataHelper.createAnnotations();
		UserDataHelper.createUsers();
		
		this.dataHelpersInitialized = true;
	}
	
	public boolean isDataHelpersInitialized() {
		return this.dataHelpersInitialized;
	}
	
	private void clearDatabase() {
		List<Class<? extends IBean>> entityTypes = Arrays.asList(
			UserBean.class, VerseBean.class, AnnotationBean.class,
			ChapterBean.class, BookBean.class, BibleBean.class
		);
		
		for (Class<? extends IBean> entityType : entityTypes) {
			DaoFactory.createDao(entityType).deleteAll();
		}
	}
}