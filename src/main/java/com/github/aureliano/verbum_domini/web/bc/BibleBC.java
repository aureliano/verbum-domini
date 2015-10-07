package com.github.aureliano.verbum_domini.web.bc;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.web.DataPage;

public final class BiblesBC {

	private BiblesBC() {
		super();
	}
	
	public static DataPage createDataPage(BibleBean filter, Integer pageIndex) {
		if (pageIndex == null) {
			pageIndex = 1;
		}
		
		final int pageSize = 10;
		final int firstElement = (pageIndex * pageSize - (pageSize - 1));
		
		Pagination<BibleBean> pagination = DaoFactory.createDao(BibleBean.class)
				.list(filter, firstElement, pageSize);
		
		return new DataPage()
			.withData(pagination.getElements())
			.withTotal(pagination.getSize())
			.withPageSize(pageSize)
			.withPageIndex(pageIndex);
	}
	
	public static BibleBean fetchBible(Integer id) {
		return DaoFactory.createDao(BibleBean.class).load(id);
	}
}