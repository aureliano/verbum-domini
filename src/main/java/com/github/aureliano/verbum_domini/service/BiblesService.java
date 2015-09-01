package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.domain.dao.BibleDao;
import com.github.aureliano.verbum_domini.domain.dao.Pagination;
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
	
	public static Bible fetchById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		BibleBean bean = new BibleDao().get(Integer.parseInt(id));
		return (bean == null) ? null : bean.toResource();
	}
}