package com.github.aureliano.verbum_domini.web.mb;

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
	
	public void signIn() {
		List<FacesMessage> messages = SignInBC.authenticate(this.login, this.password);
		if (messages.isEmpty()) {
			logger.info("User " + this.login + " has just signed in.");
			WebHelper.setSessionAttribute("user_login", this.login);
			WebHelper.sendRedirect("/verbumdomini/");
			
			return;
		}
		
		WebHelper.addMessagesToContext(messages);
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