package com.github.aureliano.verbum_domini.core.bean;

import java.util.List;

public interface VerseBean extends IBean {

	public abstract void setNumber(String number);
	
	public abstract String getNumber();
	
	public abstract void setText(String text);
	
	public abstract String getText();
	
	public abstract void setChapter(ChapterBean chapter);
	
	public abstract ChapterBean getChapter();
	
	public abstract void setAnnotations(List<AnnotationBean> annotations);
	
	public abstract List<AnnotationBean> getAnnotations();
	
	public abstract VerseBean addAnnotation(AnnotationBean annotation);
}