package com.github.aureliano.verbum_domini.dao.helper;

import com.github.aureliano.verbum_domini.orm.PersistenceManager;

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
		
		PersistenceManager.instance().startUp();
		BibleDataHelper.createBibles();
		BookDataHelper.createBooks();
		ChapterDataHelper.createChapters();
		VerseDataHelper.createChapters();
		AnnotationDataHelper.createAnnotations();
		
		this.dataHelpersInitialized = true;
	}
	
	public boolean isDataHelpersInitialized() {
		return this.dataHelpersInitialized;
	}
}