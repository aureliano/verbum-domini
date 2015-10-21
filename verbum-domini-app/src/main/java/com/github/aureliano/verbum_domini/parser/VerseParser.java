package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.model.Verse;

public class VerseParser implements IResourceParser<VerseBean> {

	public VerseParser() {
		super();
	}
	
	@Override
	public Verse toResource(VerseBean bean) {
		Integer chapterId = (bean.getChapter() != null) ? bean.getChapter().getId() : null;
		
		return new Verse()
			.withVerseId(bean.getId())
			.withChapterId(chapterId)
			.withNumber(bean.getNumber())
			.withText(bean.getText());
	}
}