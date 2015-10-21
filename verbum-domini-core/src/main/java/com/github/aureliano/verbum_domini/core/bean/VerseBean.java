package com.github.aureliano.verbum_domini.core.bean;

public interface VerseBean extends IBean {

	public abstract void setNumber(String number);
	
	public abstract String getNumber();
	
	public abstract void setText(String text);
	
	public abstract String getText();
	
	public abstract void setChapter(ChapterBean chapter);
	
	public abstract ChapterBean getChapter();
}