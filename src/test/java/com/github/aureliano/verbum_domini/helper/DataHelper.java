package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;

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
		BibleDataHelper.createBibles();
		BookDataHelper.createBooks();
		ChapterDataHelper.createChapters();
		VerseDataHelper.createVerses();
		AnnotationDataHelper.createAnnotations();
		
		this.dataHelpersInitialized = true;
	}
	
	public boolean isDataHelpersInitialized() {
		return this.dataHelpersInitialized;
	}
}