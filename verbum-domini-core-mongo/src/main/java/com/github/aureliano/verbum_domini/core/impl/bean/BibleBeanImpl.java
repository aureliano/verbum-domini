package com.github.aureliano.verbum_domini.core.impl.bean;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.validation.Save;

public class BibleBeanImpl implements BibleBean, Bson {

	private static final long serialVersionUID = -7811977913345203476L;
	
	private Integer id;

	@NotNull(message = "Property 'Name' must be provided.", groups = { Save.class })
	@Size(min = 3, max = 100, message = "Property 'Name' must have between 3 and 100 characters.", groups = { Save.class })
	private String name;

	@NotNull(message = "Property 'Language' must be provided.", groups = { Save.class })
	@Size(min = 3, max = 50, message = "Property 'Language' must have between 3 and 50 characters.", groups = { Save.class })
	private String language;

	@Size(min = 5, max = 200, message = "Property 'URL' must have between 5 and 200 characters.", groups = { Save.class })
	private String url;

	@Size(min = 3, max = 100, message = "Property 'Edition' must have between 3 and 100 characters.", groups = { Save.class })
	private String edition;

	@Size(min = 3, max = 300, message = "Property 'Printed source' must have between 3 and 300 characters.", groups = { Save.class })
	private String printedSource;

	@Size(min = 3, max = 200, message = "Property 'Eletronic transcription source' must have between 3 and 200 characters.", groups = { Save.class })
	private String eletronicTranscriptionSource;

	@Size(min = 3, max = 200, message = "Property 'Eletronic transcription source URL' must have between 3 and 200 characters.", groups = { Save.class })
	private String eletronicTranscriptionSourceUrl;

	@Size(min = 3, max = 200, message = "Property 'Copyright' must have between 3 and 200 characters.", groups = { Save.class })
	private String copyright;
	
	private List<BookBean> books;

	public BibleBeanImpl() {
		super();
	}

	public Integer getId() {
		return this.id;
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

	public void setEletronicTranscriptionSourceUrl(
			String eletronicTranscriptionSourceUrl) {
		this.eletronicTranscriptionSourceUrl = eletronicTranscriptionSourceUrl;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public List<BookBean> getBooks() {
		return books;
	}

	public void setBooks(List<BookBean> books) {
		this.books = books;
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
		BibleBeanImpl other = (BibleBeanImpl) obj;
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

	@Override
	public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
		return new BsonDocumentWrapper<BibleBeanImpl>(this, codecRegistry.get(BibleBeanImpl.class));
	}
}