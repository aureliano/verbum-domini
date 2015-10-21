package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.github.aureliano.verbum_domini.helper.ServiceHelper;
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Bibles;
import com.github.aureliano.verbum_domini.parser.ResourceToEntityParser;

public final class BiblesService {

	private BiblesService() {
		super();
	}
	
	public static Bibles fetchAll(Long start, Long pages) {
		Pagination<BibleBean> beans = DaoFactory.createDao(BibleBean.class)
				.list(new ServiceParams().withStart(start).withPages(pages));
		List<Bible> bibles = new ArrayList<Bible>();
		
		for (BibleBean bean : beans.getElements()) {
			bibles.add(ResourceToEntityParser.parse(Bible.class, bean));
		}
		
		return new Bibles().withBibles(bibles).withSize(beans.getSize());
	}
	
	public static Bible fetchById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		BibleBean bean = DaoFactory.createDao(BibleBean.class).get(Integer.parseInt(id));
		return (bean == null) ? null : ResourceToEntityParser.parse(Bible.class, bean);
	}
	
	public static boolean exist(String id) {
		return ServiceHelper.exist(BibleBean.class, id);
	}
}