package com.github.aureliano.verbum_domini.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.aureliano.verbum_domini.model.Annotation;

@Entity
@Table(name = "annotation")
public class AnnotationBean implements IBean {

	private static final long serialVersionUID = 6527131617359623209L;

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "number", precision = 5, nullable = false)
	private String number;
	
	@Column(name = "text", precision = 10000, nullable = false)
	private String text;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ChapterBean.class, optional = false)
	@JoinColumn(name = "chapter_fk")
	private ChapterBean chapter;
	
	public AnnotationBean() {
		super();
	}

	public Integer getId() {
		return id;
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
	
	@SuppressWarnings("unchecked")
	public Annotation toResource() {
		return new Annotation()
			.withAnnotationId(this.id)
			.withNumber(this.number)
			.withText(this.text)
			.withChapterId(this.chapter.getId());
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
		AnnotationBean other = (AnnotationBean) obj;
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
}