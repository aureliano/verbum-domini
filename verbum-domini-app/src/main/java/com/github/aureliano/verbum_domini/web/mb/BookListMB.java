package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.DataPage;
import com.github.aureliano.verbum_domini.web.NavigationViewKey;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;
import com.github.aureliano.verbum_domini.web.bc.BookBC;

@ManagedBean(name = "bookListMB")
@ViewScoped
public class BookListMB {
	
	private static final String PAGE_URL = UrlHelper.buildWebAppUrl("app/books");
	
	private BibleBean bible;
	private DataPage dataPage;
	
	public BookListMB() {
		super();
	}
	
	public void preRender() {
		Integer id = (Integer) WebHelper.getSessionAttribute("bible.id");
		if (id == null) {
			id = WebHelper.getEntityIdFromRequest("bible.id");
		}
		
		this.bible = BibleBC.fetchBible(id);
		
		Integer page = WebHelper.getCurrentDataPage();
		this.dataPage = BookBC.createDataPage(this.createFilter(), page);
	}
	
	public String detail() {
		return NavigationViewKey.BOOK_DETAIL.name();
	}
	
	public String importBook() {
		return NavigationViewKey.IMPORT_BOOK.name();
	}

	public BibleBean getBible() {
		return bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}
	
	public boolean isEmptyData() {
		return this.dataPage.getData().isEmpty();
	}

	public DataPage getDataPage() {
		return dataPage;
	}

	public void setDataPage(DataPage dataPage) {
		this.dataPage = dataPage;
	}
	
	public String getPageUrl() {
		return PAGE_URL;
	}
	
	private BookBean createFilter() {
		BookBean filter = new BookBeanImpl();
		filter.setBible(this.bible);
		
		return filter;
	}
}