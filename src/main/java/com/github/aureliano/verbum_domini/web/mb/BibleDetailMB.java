package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
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
		Integer id = this.bibleId();
		this.bible = BibleBC.fetchBible(id);
	}
	
	public String edit() {
		return NavigationViewKey.BIBLE_EDIT.name();
	}

	public BibleBean getBible() {
		return bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}
	
	private Integer bibleId() {
		Object id = WebHelper.getRequestParameter("bible.id");
		if (id == null) {
			throw new VerbumDominiException("Could not find any bible id in the request.");
		}
		
		return Integer.parseInt(id.toString());
	}
}