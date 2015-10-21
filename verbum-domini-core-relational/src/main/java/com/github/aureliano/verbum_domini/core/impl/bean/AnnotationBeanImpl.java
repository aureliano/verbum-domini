package com.github.aureliano.verbum_domini.core.impl.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.validation.Delete;
import com.github.aureliano.verbum_domini.core.validation.Save;

@Entity
@Table(name = "annotation")
public class AnnotationBeanImpl implements AnnotationBean {

	private static final long serialVersionUID = 6527131617359623209L;
	
	@Id
	@Column(name = "id", nullable = false)
	@NotNull(message = "Property 'Id' must be provided.", groups = { Save.class, Delete.class })
	private Integer id;
	
	@Column(name = "number", precision = 5, nullable = false)
	@NotNull(message = "Property 'Number' must be provided.", groups = { Save.class })
	@Size(min = 1, max = 5, message = "Property 'Number' must have between 1 and 5 characters.", groups = { Save.class })
	private String number;
    
	@Column(name = "text", precision = 10000, nullable = false)
	@NotNull(message = "Property 'Text' must be provided.", groups = { Save.class })
	@Size(min = 5, max = 10000, message = "Property 'Text' must have between 5 and 10000 characters.", groups = { Save.class })
	private String text;
    
    @ManyToOne(targetEntity = ChapterBeanImpl.class)
	@JoinColumn(name = "chapter_fk")
    @NotNull(message = "Property 'Chapter' must be provided.", groups = { Save.class })
    private ChapterBean chapter;
	
	public AnnotationBeanImpl() {
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
		AnnotationBeanImpl other = (AnnotationBeanImpl) obj;
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