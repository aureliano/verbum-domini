package com.github.aureliano.verbum_domini.web.mb;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;
import com.github.aureliano.verbum_domini.web.bc.SignInBC;

@ManagedBean(name = "signInMB")
@RequestScoped
public class SignInMB {
	
	private static final Logger logger = Logger.getLogger(SignInMB.class);

	private String login;
	private String password;
	
	public SignInMB() {
		super();
	}
	
	public void showAccessDeniedMessage() {
		Object attr = WebHelper.removeSessionAttribute(SessionKey.ACCESS_DENIED.name());
		if (attr != null) {
			String message = "User authentication must be provided before accessing this resource.";
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			WebHelper.addMessagesToContext(Arrays.asList(fm));
		}
	}
	
	public void signIn() {
		List<FacesMessage> messages = SignInBC.authenticate(this.login, this.password);
		
		if (!messages.isEmpty()) {
			WebHelper.addMessagesToContext(messages);
			return;
		}
		
		logger.info("User " + this.login + " has just signed in.");
		WebHelper.setSessionAttribute(SessionKey.USER_LOGIN.name(), this.login);
		
		Object requestedUri = WebHelper.removeSessionAttribute(SessionKey.REQUESTED_URI.name());
		if (requestedUri != null) {
			WebHelper.sendRedirect(requestedUri.toString());
		} else {
			WebHelper.sendRedirect("/verbumdomini/");
		}
	}
	
	public boolean isUserSignedIn() {
		return WebHelper.getSessionAttribute(SessionKey.USER_LOGIN.name()) != null;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}