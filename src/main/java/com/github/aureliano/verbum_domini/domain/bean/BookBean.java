package com.github.aureliano.verbum_domini.domain.bean;

import com.github.aureliano.verbum_domini.model.Book;

public class BookBean implements IBean {

	private static final long serialVersionUID = -240907626446100682L;

	private Integer id;
	private String name;
	private BibleBean bible;
	
	public BookBean() {
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

	public BibleBean getBible() {
		return bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}
	
	@SuppressWarnings("unchecked")
	public Book toResource() {
		return new Book()
			.withBookId(this.id)
			.withName(this.name)
			.withBibleId(this.bible.getId());
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
		BookBean other = (BookBean) obj;
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
}