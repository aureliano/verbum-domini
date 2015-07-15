package com.github.aureliano.verbum_domini.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.github.aureliano.verbum_domini.model.Bible;

@Entity
@Table(name = "bible")
public class BibleBean implements Serializable {

	private static final long serialVersionUID = -9140101263038741687L;
	
	@Id
	@SequenceGenerator(name = "bible_sequence", schema = "public", sequenceName = "bible_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bible_sequence")
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", precision = 100, nullable = false)
	private String name;

	@Column(name = "language", precision = 50, nullable = false)
	private String language;

	@Column(name = "url", precision = 200, nullable = true)
	private String url;

	@Column(name = "edition", precision = 100, nullable = true)
	private String edition;

	@Column(name = "printed_source", precision = 300, nullable = true)
	private String printedSource;

	@Column(name = "eletronic_transcription_source", precision = 200, nullable = true)
	private String eletronicTranscriptionSource;

	@Column(name = "eletronic_transcription_source_url", precision = 200, nullable = true)
	private String eletronicTranscriptionSourceUrl;

	@Column(name = "copyright", precision = 200, nullable = true)
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
	
	public Bible toResource() {
		return new Bible()
			.withId(this.id)
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