package com.github.aureliano.verbum_domini.core.bean;

import java.util.List;

public interface BibleBean extends IBean {

	public abstract void setName(String name);
	
	public abstract String getName();
	
	public abstract void setLanguage(String language);
	
	public abstract String getLanguage();
	
	public abstract void setUrl(String url);
	
	public abstract String getUrl();
	
	public abstract void setEdition(String edition);
	
	public abstract String getEdition();
	
	public abstract void setPrintedSource(String printedSource);
	
	public abstract String getPrintedSource();
	
	public abstract void setEletronicTranscriptionSource(String eletronicTranscriptionSource);
	
	public abstract String getEletronicTranscriptionSource();
	
	public abstract void setEletronicTranscriptionSourceUrl(String eletronicTranscriptionSourceUrl);
	
	public abstract String getEletronicTranscriptionSourceUrl();
	
	public abstract void setCopyright(String copyright);
	
	public abstract String getCopyright();
	
	public abstract void setBooks(List<BookBean> books);
	
	public abstract List<BookBean> getBooks();
}