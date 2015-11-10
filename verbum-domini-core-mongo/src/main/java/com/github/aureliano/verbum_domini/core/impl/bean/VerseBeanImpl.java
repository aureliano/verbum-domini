package com.github.aureliano.verbum_domini.core.impl.bean;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.validation.group.Save;

public class VerseBeanImpl implements VerseBean, Bson {

	private static final long serialVersionUID = 5260974792736819231L;

	private Integer id;
	
	@NotNull(message = "Property 'Number' must be provided.", groups = { Save.class })
	@Size(min = 1, max = 5, message = "Property 'Number' must have between 1 and 5 characters.")
	private String number;
    
	@NotNull(message = "Property 'Text' must be provided.", groups = { Save.class })
	@Size(min = 5, max = 10000, message = "Property 'Text' must have between 5 and 10000 characters.", groups = { Save.class })
	private String text;
    
    @NotNull(message = "Property 'Chapter' must be provided.", groups = { Save.class })
    private ChapterBean chapter;
    
    private List<AnnotationBean> annotations;
	
	public VerseBeanImpl() {
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ChapterBean getChapter() {
		return chapter;
	}

	public void setChapter(ChapterBean chapter) {
		this.chapter = chapter;
	}

	public void setAnnotations(List<AnnotationBean> annotations) {
		this.annotations = annotations;
	}

	public List<AnnotationBean> getAnnotations() {
		return this.annotations;
	}
	
	public VerseBeanImpl addAnnotation(AnnotationBean annotation) {
		if (this.annotations == null) {
			this.annotations = new ArrayList<AnnotationBean>();
		}
		
		this.annotations.add(annotation);
		return this;
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
		VerseBeanImpl other = (VerseBeanImpl) obj;
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
		return new BsonDocumentWrapper<VerseBeanImpl>(this, codecRegistry.get(VerseBeanImpl.class));
	}
}