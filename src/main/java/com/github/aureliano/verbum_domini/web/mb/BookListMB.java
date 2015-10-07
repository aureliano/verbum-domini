package com.github.aureliano.verbum_domini.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.DataPage;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;

@ManagedBean(name = "bookListMB")
@ViewScoped
public class BookListMB {
	
	private BibleBean bible;
	private DataPage dataPage;
	
	public BookListMB() {
		super();
	}
	
	@PostConstruct
	public void preRender() {
		Integer id = this.bibleId();
		this.bible = BibleBC.fetchBible(id);
		
		Integer page = WebHelper.getCurrentDataPage();
		this.dataPage = null;//BibleBC.createDataPage(null, page);
	}

	public BibleBean getBible() {
		return bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}

	public DataPage getDataPage() {
		return dataPage;
	}

	public void setDataPage(DataPage dataPage) {
		this.dataPage = dataPage;
	}
	
	private Integer bibleId() {
		Object id = WebHelper.getRequestParameter("bible.id");
		if (id == null) {
			throw new VerbumDominiException("Could not find any bible id in the request.");
		}
		
		return Integer.parseInt(id.toString());
	}
}