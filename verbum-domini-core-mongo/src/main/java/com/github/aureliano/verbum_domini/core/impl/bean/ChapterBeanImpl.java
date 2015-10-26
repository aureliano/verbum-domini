package com.github.aureliano.verbum_domini.core.impl.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.validation.Save;

public class ChapterBeanImpl implements ChapterBean, Bson {

	private static final long serialVersionUID = 997809325475133176L;

	private Integer id;

	@NotNull(message = "Property 'Number' must be provided.", groups = { Save.class })
	private String number;

	@NotNull(message = "Property 'Book' must be provided.", groups = { Save.class })
	private BookBean book;
	
	private List<VerseBean> verses;
	
	private List<AnnotationBean> annotations;

	public ChapterBeanImpl() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}

	public List<VerseBean> getVerses() {
		return verses;
	}

	public void setVerses(List<VerseBean> verses) {
		this.verses = verses;
	}

	public List<AnnotationBean> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationBean> annotations) {
		this.annotations = annotations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		ChapterBeanImpl other = (ChapterBeanImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
	
	@Override
	public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
		return new BsonDocumentWrapper<ChapterBeanImpl>(this, codecRegistry.get(ChapterBeanImpl.class));
	}
}