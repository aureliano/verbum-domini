package com.github.aureliano.verbum_domini.web.ui;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.github.aureliano.verbum_domini.helper.UrlHelper;

@FacesComponent(
		value = "com.github.aureliano.verbum_domini.web.ui.HtmlStyleSheet",
		tagName = "styleSheet", createTag = true
	)
public class HtmlStyleSheet extends UIComponentBase {

	public HtmlStyleSheet() {
		super();
	}

	@Override
	public String getFamily() {
		return "custom.ui.verbum_domini";
	}
	
	@Override
	public void encodeBegin(FacesContext facesContext) throws IOException {
		StringBuilder tag = new StringBuilder();

		tag.append("<link rel=\"stylesheet\" type=\"text/css\"");
		
		String source = (String) super.getAttributes().get("source");
		if (source != null) {
			String url = UrlHelper.buildWebAppUrl("css/" + source + ".css");
			tag.append(" href=\"" + url + "\"");
		}
		
		String media = (String) super.getAttributes().get("media");
		if (media != null) {
			tag.append(" media=\"" + media + "\"");
		}
		
		tag.append("/>");
		
		facesContext.getResponseWriter().write(tag.toString());
	}
}