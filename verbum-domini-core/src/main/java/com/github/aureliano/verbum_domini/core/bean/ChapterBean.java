package com.github.aureliano.verbum_domini.core.bean;

import java.util.List;

public interface ChapterBean extends IBean {

	public abstract void setNumber(String number);
	
	public abstract String getNumber();
	
	public abstract void setBook(BookBean book);
	
	public abstract BookBean getBook();
	
	public abstract void setVerses(List<VerseBean> verses);
	
	public abstract List<VerseBean> getVerses();
	
	public abstract void setAnnotations(List<AnnotationBean> annotations);
	
	public abstract List<AnnotationBean> getAnnotations();
}