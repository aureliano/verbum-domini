package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.NavigationViewKey;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;

@ManagedBean
@RequestScoped
public class BibleDetailMB {

	private BibleBean bible;
	
	public BibleDetailMB() {
		super();
	}
	
	public void preRender() {
		Integer id = WebHelper.getEntityIdFromRequest("bible.id");
		this.bible = BibleBC.fetchBible(id);
	}
	
	public String edit() {
		return NavigationViewKey.BIBLE_EDIT.name();
	}
	
	public String listBooks() {
		return NavigationViewKey.BIBLE_BOOKS.name();
	}

	public BibleBean getBible() {
		return bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}
}