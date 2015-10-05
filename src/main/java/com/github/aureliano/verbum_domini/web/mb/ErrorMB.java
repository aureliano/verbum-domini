package com.github.aureliano.verbum_domini.web.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;

@ManagedBean
@RequestScoped
public class ErrorMB {

	private String server;
	private String uuid;
	private Date serverDatetime;
	private List<String> errors;
	
	@SuppressWarnings("unchecked")
	public ErrorMB() {
		this.uuid = UUID.randomUUID().toString();
		this.server = WebHelper.getServerIpAddress();
		this.serverDatetime = new Date();
		
		Object value = WebHelper.removeSessionAttribute(SessionKey.ERROR_MESSAGES.name());
		this.errors = (value != null) ? (List<String>) value : new ArrayList<String>();
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Date getServerDatetime() {
		return serverDatetime;
	}

	public void setServerDatetime(Date eventDatetime) {
		this.serverDatetime = eventDatetime;
	}
}