package com.github.aureliano.verbum_domini.web.mb;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.helper.WebHelper;
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
		Object attr = WebHelper.removeSessionAttribute(WebHelper.ACCESS_DENIED_KEY);
		if (attr != null) {
			String message = "User authentication must be provided before accessing this resource.";
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			WebHelper.addMessagesToContext(Arrays.asList(fm));
		}
	}
	
	public void signIn() {
		List<FacesMessage> messages = SignInBC.authenticate(this.login, this.password);
		if (messages.isEmpty()) {
			logger.info("User " + this.login + " has just signed in.");
			WebHelper.setSessionAttribute(WebHelper.USER_LOGIN_KEY, this.login);
			
			Object requestedUri = WebHelper.removeSessionAttribute("requestedUri");
			if (requestedUri != null) {
				WebHelper.sendRedirect(requestedUri.toString());
			} else {
				WebHelper.sendRedirect("/verbumdomini/");
			}
			
			return;
		}
		
		WebHelper.addMessagesToContext(messages);
	}
	
	public boolean isUserSignedIn() {
		return WebHelper.getSessionAttribute(WebHelper.USER_LOGIN_KEY) != null;
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