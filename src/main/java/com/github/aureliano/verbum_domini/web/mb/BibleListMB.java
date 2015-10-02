package com.github.aureliano.verbum_domini.web.mb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.github.aureliano.verbum_domini.web.DataPage;
import com.github.aureliano.verbum_domini.web.bc.BiblesBC;

@ManagedBean(name = "bibleListMB")
@ViewScoped
public class BibleListMB {

	private static final Pattern PATTERN = Pattern.compile("[?&]?page=(\\d+)");
	
	private DataPage dataPage;
	
	public BibleListMB() {
		super();
	}
	
	public void preRender() {
		Integer page = this.currentPage();
		this.dataPage = BiblesBC.createDataPage(null, page);
	}
	
	public String detail() {
		return "BIBLE_DETAIL";
	}
	
	public DataPage getDataPage() {
		return this.dataPage;
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
}