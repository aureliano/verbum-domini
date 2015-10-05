package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.bc.BiblesBC;

@ManagedBean
@ViewScoped
public class BibleEditMB {

	private BibleBean bible;
	
	public BibleEditMB() {
		super();
	}
	
	public void preRender() {
		System.out.println("\n\n ==>> Executing preRender");
		Integer id = this.bibleId();
		this.bible = (id != null) ? BiblesBC.fetchBible(id) : new BibleBeanImpl();
		System.out.println(" ==>> BIBLE: " + this.bible + "\n\n");
	}
	
	public void save() {
		System.out.println("\n\n => Calling save");
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
			return null;
		}
		
		return Integer.parseInt(id.toString());
	}
}