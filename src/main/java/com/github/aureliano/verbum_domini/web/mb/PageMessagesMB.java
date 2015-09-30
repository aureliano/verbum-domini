package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "pageMessagesMB")
@RequestScoped
public class PageMessagesMB {

	public PageMessagesMB() {
		super();
	}
	
	public String getAlertStyleClass() {
		if ((this.severityLevelEquals(FacesMessage.SEVERITY_ERROR)) || (this.severityLevelEquals(FacesMessage.SEVERITY_FATAL))) {
			return "alert-danger";
		} if (this.severityLevelEquals(FacesMessage.SEVERITY_WARN)) {
			return "alert-warning";
		} else {
			return "alert-success";
		}
	}

	public boolean isExistAnyMessage() {
		return FacesContext.getCurrentInstance().getMessages().hasNext();
	}
	
	private boolean severityLevelEquals(Severity severityInfo) {
		Severity severityLevel = FacesContext.getCurrentInstance().getMaximumSeverity();

		if (severityLevel == null) {
			return false;
		}

		return severityLevel.equals(severityInfo);
	}
}