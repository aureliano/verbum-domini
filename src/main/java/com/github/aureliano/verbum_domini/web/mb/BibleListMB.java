package com.github.aureliano.verbum_domini.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.DataPage;
import com.github.aureliano.verbum_domini.web.NavigationViewKey;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;

@ManagedBean(name = "bibleListMB")
@ViewScoped
public class BibleListMB {
	
	private DataPage dataPage;
	
	public BibleListMB() {
		super();
	}
	
	@PostConstruct
	public void preRender() {
		Integer page = WebHelper.getCurrentDataPage();
		this.dataPage = BibleBC.createDataPage(null, page);
	}
	
	public String detail() {
		return NavigationViewKey.BIBLE_DETAIL.name();
	}
	
	public String add() {
		return NavigationViewKey.BIBLE_ADD.name();
	}
	
	public boolean isEmptyData() {
		return this.dataPage.getData().isEmpty();
	}
	
	public DataPage getDataPage() {
		return this.dataPage;
	}
	
	public void setDataPage(DataPage dataPage) {
		this.dataPage = dataPage;
	}
}