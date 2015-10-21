package com.github.aureliano.verbum_domini.web.ui;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.github.aureliano.verbum_domini.helper.UrlHelper;

@FacesComponent(
		value = "com.github.aureliano.verbum_domini.web.ui.HtmlScript",
		tagName = "script", createTag = true
	)
public class HtmlScript extends UIComponentBase {

	public HtmlScript() {
		super();
	}

	@Override
	public String getFamily() {
		return "custom.ui.verbum_domini";
	}
	
	@Override
	public void encodeBegin(FacesContext facesContext) throws IOException {
		StringBuilder tag = new StringBuilder();

		tag.append("<script");
		
		String source = (String) super.getAttributes().get("source");
		if (source != null) {
			String url = UrlHelper.buildWebAppUrl("js/" + source + ".js");
			tag.append(" src=\"" + url + "\"");
		}
		
		tag.append("></script>");
		
		facesContext.getResponseWriter().write(tag.toString());
	}
}