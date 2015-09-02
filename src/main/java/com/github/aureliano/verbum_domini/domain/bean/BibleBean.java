package com.github.aureliano.verbum_domini.domain.bean;

import com.github.aureliano.verbum_domini.model.Bible;

public class BibleBean implements IBean {

	private static final long serialVersionUID = -9140101263038741687L;
	
	private Integer id;
	private String name;
	private String language;
	private String url;
	private String edition;
	private String printedSource;
	private String eletronicTranscriptionSource;
	private String eletronicTranscriptionSourceUrl;
	private String copyright;
	
	public BibleBean() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getPrintedSource() {
		return printedSource;
	}

	public void setPrintedSource(String printedSource) {
		this.printedSource = printedSource;
	}

	public String getEletronicTranscriptionSource() {
		return eletronicTranscriptionSource;
	}

	public void setEletronicTranscriptionSource(String eletronicTranscriptionSource) {
		this.eletronicTranscriptionSource = eletronicTranscriptionSource;
	}

	public String getEletronicTranscriptionSourceUrl() {
		return eletronicTranscriptionSourceUrl;
	}

	public void setEletronicTranscriptionSourceUrl(String eletronicTranscriptionSourceUrl) {
		this.eletronicTranscriptionSourceUrl = eletronicTranscriptionSourceUrl;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	@SuppressWarnings("unchecked")
	public Bible toResource() {
		return new Bible()
			.withBibleId(this.id)
			.withName(this.name)
			.withLanguage(this.language)
			.withUrl(this.url)
			.withEdition(this.edition)
			.withPrintedSource(this.printedSource)
			.withEletronicTranscriptionSource(this.eletronicTranscriptionSource)
			.withEletronicTranscriptionSourceUrl(this.eletronicTranscriptionSourceUrl)
			.withCopyright(this.copyright);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BibleBean other = (BibleBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}