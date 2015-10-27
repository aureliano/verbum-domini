package com.github.aureliano.verbum_domini.web.ui;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.github.aureliano.verbum_domini.web.DataPage;

@FacesComponent(
	value = "com.github.aureliano.verbum_domini.web.ui.HtmlPaginator",
	tagName = "paginator", createTag = true
)
public class HtmlPaginator extends UIComponentBase {

	private DataPage dataPage;
	private String pageUrl;
	
	public HtmlPaginator() {
		super();
	}

	@Override
	public String getFamily() {
		return "custom.ui.verbum_domini";
	}
	
	@Override
	public void encodeBegin(FacesContext facesContext) throws IOException {
		this.configureVariables();
		
		if ((this.dataPage == null) ||
				((!this.dataPage.isHaveNextPage()) && (!this.dataPage.isHavePreviousPage()))) {
			return;
		}
		
		StringBuilder tag = new StringBuilder();
		tag.append("<nav>").append("<ul class=\"pagination\">");
		
		this.drawPreviousPaginationControls(tag);
		this.drawNumeration(tag);
		this.drawNextPaginationControls(tag);
		
		tag.append("</ul></nav>");
		
		facesContext.getResponseWriter().write(tag.toString());
	}

	private void drawNumeration(StringBuilder tag) {
		for (Integer index : this.dataPage.getPageIndexes()) {
			if (this.dataPage.getPageIndex().equals(index)) {
				tag
					.append("<li class= \"active\">")
					.append("<a href=\"javascript: void(0);\">")
					.append(index)
					.append("</a></li>");
			} else {
				tag
					.append("<li><a href=\"" + this.pageUrl + "?page=" + index + "\">")
					.append(index)
					.append("</a></li>");
			}
		}
	}
	
	private void drawPreviousPaginationControls(StringBuilder tag) {
		
		tag.append("<li");
		if (this.dataPage.isHavePreviousPageRange()) {
			tag
			.append(">")
			.append("<a href=\"" + this.pageUrl + "?page=" + this.dataPage.getPreviousPageBlock() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&lt;&lt;").append("</a>").append("</li>");
		
		tag.append("<li");
		if (this.dataPage.isHavePreviousPage()) {
			tag
			.append(">")
			.append("<a href=\"" + this.pageUrl + "?page=" + this.dataPage.getPreviousPage() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&lt;").append("</a>").append("</li>");
	}
	
	private void drawNextPaginationControls(StringBuilder tag) {
		
		tag.append("<li");
		if (this.dataPage.isHaveNextPageRange()) {
			tag
			.append(">")
			.append("<a href=\"" + this.pageUrl + "?page=" + this.dataPage.getNextPageBlock() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&gt;&gt;").append("</a>").append("</li>");
		
		tag.append("<li");
		if (this.dataPage.isHaveNextPage()) {
			tag
			.append(">")
			.append("<a href=\"" + this.pageUrl + "?page=" + this.dataPage.getNextPage() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&gt;").append("</a>").append("</li>");
	}
	
	private void configureVariables() {
		this.dataPage = (DataPage) super.getAttributes().get("dataPage");
		this.pageUrl = (String) super.getAttributes().get("pageUrl");
		if (this.pageUrl == null) {
			this.pageUrl = StringUtils.EMPTY;
		}
	}
}