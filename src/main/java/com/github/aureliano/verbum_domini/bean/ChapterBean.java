package com.github.aureliano.verbum_domini.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.aureliano.verbum_domini.model.Chapter;

@Entity
@Table(name = "chapter")
public class ChapterBean implements Serializable {

	private static final long serialVersionUID = 8638750836956946677L;

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "number", precision = 5, nullable = false)
	private String number;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = BookBean.class, optional = false)
	@JoinColumn(name = "book_fk")
	private BookBean book;
	
	public ChapterBean() {
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
	
	public Chapter toResource() {
		return new Chapter()
			.withChapterId(this.id)
			.withNumber(this.number)
			.withBookId(this.book.getId());
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
		ChapterBean other = (ChapterBean) obj;
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