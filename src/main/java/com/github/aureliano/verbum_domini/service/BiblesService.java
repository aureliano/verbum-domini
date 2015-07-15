package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.bean.BibleBean;
import com.github.aureliano.verbum_domini.dao.BibleDao;
import com.github.aureliano.verbum_domini.dao.Pagination;
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Bibles;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public final class BiblesService {

	private BiblesService() {
		super();
	}
	
	public static Bibles fetchAll(Long start, Long pages) {
		Pagination<BibleBean> beans = new BibleDao().list(new ServiceParams().withStart(start).withPages(pages));
		List<Bible> bibles = new ArrayList<Bible>();
		
		for (BibleBean bean : beans.getElements()) {
			bibles.add(bean.toResource());
		}
		
		return new Bibles().withBibles(bibles).withSize(beans.getSize());
	}
	
	public static Bible fetchByLanguage(String language) {
		BibleBean bean = new BibleDao().findByLanguage(language);
		return (bean == null) ? null : bean.toResource();
	}
}