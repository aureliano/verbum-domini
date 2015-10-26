package com.github.aureliano.verbum_domini.core.impl.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.validation.Delete;
import com.github.aureliano.verbum_domini.core.validation.Save;

@Entity
@Table(name = "chapter")
public class ChapterBeanImpl implements ChapterBean {

	private static final long serialVersionUID = 8638750836956946677L;

	@Id
	@Column(name = "id", nullable = false)
	@NotNull(message = "Property 'Id' must be provided.", groups = { Save.class, Delete.class })
	private Integer id;

	@Column(name = "number", precision = 5, nullable = false)
	@NotNull(message = "Property 'Number' must be provided.", groups = { Save.class })
	private String number;

	@ManyToOne(targetEntity = BookBeanImpl.class)
	@JoinColumn(name = "book_fk")
	@NotNull(message = "Property 'Book' must be provided.", groups = { Save.class })
	private BookBean book;
	
	@OneToMany(targetEntity = VerseBeanImpl.class, cascade = CascadeType.PERSIST, mappedBy = "chapter", fetch = FetchType.LAZY)
	private List<VerseBean> verses;
	
	@OneToMany(targetEntity = AnnotationBeanImpl.class, cascade = CascadeType.PERSIST, mappedBy = "chapter", fetch = FetchType.LAZY)
	private List<AnnotationBean> annotations;

	public ChapterBeanImpl() {
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
}