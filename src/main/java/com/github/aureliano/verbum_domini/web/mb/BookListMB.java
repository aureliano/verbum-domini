package com.github.aureliano.verbum_domini.web.mb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.DataPage;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;

@ManagedBean(name = "bookListMB")
@ViewScoped
public class BookListMB {

	private static final Pattern PATTERN = Pattern.compile("[?&]?page=(\\d+)");
	
	private BibleBean bible;
	private DataPage dataPage;
	
	public BookListMB() {
		super();
	}
	
	@PostConstruct
	public void preRender() {
		Integer id = this.bibleId();
		this.bible = BibleBC.fetchBible(id);
		System.out.println(" => BIBLE " + id);
		Integer page = this.currentPage();
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
	
	private Integer currentPage() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (StringUtils.isEmpty(request.getQueryString())) {
			return 1;
		}
		
		Matcher matcher = PATTERN.matcher(request.getQueryString());
		
		String page = null;
		if (matcher.find()) {
			page = matcher.group(1);
		}
		
		return (page != null) ? Integer.parseInt(page) : 1;
	}
	
	private Integer bibleId() {
		Object id = WebHelper.getRequestParameter("bible.id");
		if (id == null) {
			throw new VerbumDominiException("Could not find any bible id in the request.");
		}
		
		return Integer.parseInt(id.toString());
	}
}