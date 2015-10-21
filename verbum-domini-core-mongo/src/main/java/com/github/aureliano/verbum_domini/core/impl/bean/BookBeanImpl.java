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
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.validation.Save;

public class BookBeanImpl implements BookBean, Bson {

	private static final long serialVersionUID = 8971565414468135175L;

	private Integer id;

	@NotNull(message = "Property 'Name' must be provided.", groups = { Save.class })
	@Size(min = 3, max = 100, message = "Property 'Name' must have between 3 and 100 characters.", groups = { Save.class })
	private String name;

	@NotNull(message = "Property 'Bible' must be provided.", groups = { Save.class })
	private BibleBean bible;
	
	private List<ChapterBean> chapters;

	public BookBeanImpl() {
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

	public BibleBean getBible() {
		return bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}

	public List<ChapterBean> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterBean> chapters) {
		this.chapters = chapters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BookBeanImpl other = (BookBeanImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return new BsonDocumentWrapper<BookBeanImpl>(this, codecRegistry.get(BookBeanImpl.class));
	}
}