package com.github.aureliano.verbum_domini.domain.bean;

import com.github.aureliano.verbum_domini.model.Verse;

public class VerseBean implements IBean {

	private static final long serialVersionUID = -2919739849791226437L;

	private Integer id;
	private String number;
	private String text;
	private ChapterBean chapter;
	
	public VerseBean() {
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
	public Verse toResource() {
		return new Verse()
			.withVerseId(this.id)
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
		VerseBean other = (VerseBean) obj;
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