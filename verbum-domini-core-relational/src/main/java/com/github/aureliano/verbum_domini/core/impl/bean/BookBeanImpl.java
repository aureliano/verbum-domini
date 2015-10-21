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
import javax.validation.constraints.Size;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.validation.Delete;
import com.github.aureliano.verbum_domini.core.validation.Save;

@Entity
@Table(name = "book")
public class BookBeanImpl implements BookBean {

	private static final long serialVersionUID = -240907626446100682L;

	@Id
	@Column(name = "id", nullable = false)
	@NotNull(message = "Property 'Id' must be provided.", groups = { Save.class, Delete.class })
	private Integer id;

	@Column(name = "name", precision = 100, nullable = false)
	@NotNull(message = "Property 'Name' must be provided.", groups = { Save.class })
	@Size(min = 3, max = 100, message = "Property 'Name' must have between 3 and 100 characters.", groups = { Save.class })
	private String name;

	@ManyToOne(targetEntity = BibleBeanImpl.class)
	@JoinColumn(name = "bible_fk")
	@NotNull(message = "Property 'Bible' must be provided.", groups = { Save.class })
	private BibleBean bible;
	
	@OneToMany(targetEntity = ChapterBeanImpl.class, cascade = CascadeType.PERSIST, mappedBy = "book", fetch = FetchType.LAZY)
	private List<ChapterBean> chapters;

	public BookBeanImpl() {
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
}