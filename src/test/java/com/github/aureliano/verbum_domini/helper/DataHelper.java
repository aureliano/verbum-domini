package com.github.aureliano.verbum_domini.helper;

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
		
		this.createTables();
		this.createData();
		
		this.dataHelpersInitialized = true;
	}
	
	public boolean isDataHelpersInitialized() {
		return this.dataHelpersInitialized;
	}
	
	private void createTables() {
		BibleDataHelper.createTable();
		BookDataHelper.createTable();
		ChapterDataHelper.createTable();
		VerseDataHelper.createTable();
		AnnotationDataHelper.createTable();
	}
	
	private void createData() {
		BibleDataHelper.createBibles();
		BookDataHelper.createBooks();
		ChapterDataHelper.createChapters();
		VerseDataHelper.createChapters();
		AnnotationDataHelper.createAnnotations();
	}
}