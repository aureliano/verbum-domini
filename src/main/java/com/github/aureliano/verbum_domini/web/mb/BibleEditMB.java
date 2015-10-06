package com.github.aureliano.verbum_domini.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.bc.BiblesBC;

@ManagedBean(name = "bibleEditMB")
@ViewScoped
public class BibleEditMB {

	private static final Logger logger = Logger.getLogger(BibleEditMB.class);
	
	private BibleBean bible;
	
	public BibleEditMB() {
		super();
	}
	
	@PostConstruct
	public void preRender() {
		Integer id = this.bibleId();
		logger.info("Preparing to edit bible with id " + id);
		this.bible = (id != null) ? BiblesBC.fetchBible(id) : new BibleBeanImpl();
	}
	
	public void save() {
		logger.info("Saving bible with id " + this.bible.getId());
	}

	public BibleBean getBible() {
		return this.bible;
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