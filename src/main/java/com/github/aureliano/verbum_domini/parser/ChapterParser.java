package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.model.Chapter;

public class ChapterParser implements IResourceParser<ChapterBean> {

	public ChapterParser() {
		super();
	}

	@Override
	public Chapter toResource(ChapterBean bean) {
		Integer bookId = (bean.getBook() != null) ? bean.getBook().getId() : null;
		
		return new Chapter()
			.withChapterId(bean.getId())
			.withBookId(bookId)
			.withNumber(bean.getNumber());
	}
}