package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;

@ManagedBean(name = "bibleEditMB")
@ViewScoped
public class BibleEditMB {

	private static final Logger logger = Logger.getLogger(BibleEditMB.class);
	
	private BibleBean bible;
	
	public BibleEditMB() {
		super();
	}
	
	public void preRender() {
		Integer id = WebHelper.getEntityIdFromRequest("bible.id");
		logger.info("Preparing to edit bible with id " + id);
		this.bible = (id != null) ? BibleBC.fetchBible(id) : new BibleBeanImpl();
	}
	
	public void save() {
		try {
			BibleBC.save(this.bible);
			WebHelper.setSessionAttribute(SessionKey.INFO_MESSAGE.name(), "Bible saved successfuly.");
			logger.info("Bible with id " + this.bible.getId() + " saved successfuly.");
			
			String url = UrlHelper.buildWebAppUrl("app/bibles");
			WebHelper.sendRedirect(url);
		} catch (ValidationException ex) {
			logger.warn("Bible bean validation has failed.");
		}
	}

	public BibleBean getBible() {
		return this.bible;
	}

	public void setBible(BibleBean bible) {
		this.bible = bible;
	}
}