package com.github.aureliano.verbum_domini.core.bean;

import java.util.List;

public interface BookBean extends IBean {
	
	public abstract void setName(String name);
	
	public abstract String getName();
	
	public abstract void setBible(BibleBean bible);
	
	public abstract BibleBean getBible();
	
	public abstract void setChapters(List<ChapterBean> chapters);
	
	public abstract List<ChapterBean> getChapters();
}