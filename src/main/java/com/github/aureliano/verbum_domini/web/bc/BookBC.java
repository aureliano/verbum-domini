package com.github.aureliano.verbum_domini.web.bc;

import org.apache.commons.lang.StringUtils;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.web.DataPage;

public final class BookBC {

	private BookBC() {
		super();
	}
	
	public static DataPage createDataPage(BookBean filter, Integer pageIndex) {
		if (pageIndex == null) {
			pageIndex = 1;
		}
		
		final int pageSize = 10;
		final int firstElement = (pageIndex * pageSize - (pageSize - 1));
		
		Pagination<BookBean> pagination = DaoFactory.createDao(BookBean.class)
				.list(filter, firstElement, pageSize);
		
		return new DataPage()
			.withData(pagination.getElements())
			.withTotal(pagination.getSize())
			.withPageSize(pageSize)
			.withPageIndex(pageIndex);
	}
	
	public static BookBean fetchBook(Integer id) {
		BookBean book = DaoFactory.createDao(BookBean.class).load(id);
		if (book.getBible() != null && StringUtils.isEmpty(book.getBible().getName())) {
			BibleBean bible = BibleBC.fetchBible(book.getBible().getId());
			book.setBible(bible);
		}
		
		return book;
	}
}