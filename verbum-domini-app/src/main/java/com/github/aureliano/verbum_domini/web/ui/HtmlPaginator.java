package com.github.aureliano.verbum_domini.web.ui;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.github.aureliano.verbum_domini.web.DataPage;

@FacesComponent(
	value = "com.github.aureliano.verbum_domini.web.ui.HtmlPaginator",
	tagName = "paginator", createTag = true
)
public class HtmlPaginator extends UIComponentBase {

	public HtmlPaginator() {
		super();
	}

	@Override
	public String getFamily() {
		return "custom.ui.verbum_domini";
	}
	
	@Override
	public void encodeBegin(FacesContext facesContext) throws IOException {
		DataPage dataPage = (DataPage) super.getAttributes().get("dataPage");
		
		if ((dataPage == null) || (dataPage.getPageIndex() <= 1)) {
			return;
		}
		
		StringBuilder tag = new StringBuilder();
		tag.append("<nav>").append("<ul class=\"pagination\">");
		
		this.drawPreviousPaginationControls(tag, dataPage);
		this.drawNumeration(tag, dataPage);
		this.drawNextPaginationControls(tag, dataPage);
		
		tag.append("</ul></nav>");
		
		facesContext.getResponseWriter().write(tag.toString());
	}

	private void drawNumeration(StringBuilder tag, DataPage dataPage) {
		for (Integer index : dataPage.getPageIndexes()) {
			if (dataPage.getPageIndex().equals(index)) {
				tag
					.append("<li class= \"active\">")
					.append("<a href=\"javascript: void(0);\">")
					.append(index)
					.append("</a></li>");
			} else {
				tag
					.append("<li><a href=\"?page=" + index + "\">")
					.append(index)
					.append("</a></li>");
			}
		}
	}
	
	private void drawPreviousPaginationControls(StringBuilder tag,
			DataPage dataPage) {
		
		tag.append("<li");
		if (dataPage.isHavePreviousPageRange()) {
			tag
			.append(">")
			.append("<a href=\"?page=" + dataPage.getPreviousPageBlock() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&lt;&lt;").append("</a>").append("</li>");
		
		tag.append("<li");
		if (dataPage.isHavePreviousPage()) {
			tag
			.append(">")
			.append("<a href=\"?page=" + dataPage.getPreviousPage() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&lt;").append("</a>").append("</li>");
	}
	
	private void drawNextPaginationControls(StringBuilder tag,
			DataPage dataPage) {
		
		tag.append("<li");
		if (dataPage.isHaveNextPageRange()) {
			tag
			.append(">")
			.append("<a href=\"?page=" + dataPage.getNextPageBlock() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&gt;&gt;").append("</a>").append("</li>");
		
		tag.append("<li");
		if (dataPage.isHaveNextPage()) {
			tag
			.append(">")
			.append("<a href=\"?page=" + dataPage.getNextPage() + "\">");
		} else {
			tag
			.append(" class=\"disabled\">")
			.append("<a href=\"javascript: void(0);\">");
		}
		
		tag.append("&gt;").append("</a>").append("</li>");
	}
}