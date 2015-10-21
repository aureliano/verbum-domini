package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.model.Bible;

public class BibleParser implements IResourceParser<BibleBean> {

	public BibleParser() {
		super();
	}
	
	@Override
	public Bible toResource(BibleBean bean) {
		return new Bible()
			.withBibleId(bean.getId())
			.withCopyright(bean.getCopyright())
			.withEdition(bean.getEdition())
			.withEletronicTranscriptionSource(bean.getEletronicTranscriptionSource())
			.withEletronicTranscriptionSourceUrl(bean.getEletronicTranscriptionSourceUrl())
			.withLanguage(bean.getLanguage())
			.withName(bean.getName())
			.withPrintedSource(bean.getPrintedSource())
			.withUrl(bean.getUrl());
	}
}