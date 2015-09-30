package com.github.aureliano.verbum_domini.web.bc;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import com.github.aureliano.verbum_domini.core.impl.dao.UserDaoImpl;

public final class SignInBC {

	private SignInBC() {
		super();
	}
	
	public static List<FacesMessage> authenticate(String login, String password) {
		List<FacesMessage> messages = new ArrayList<>();
		
		try {
			boolean authenticated = new UserDaoImpl().authenticate(login, password);

			if (!authenticated) {
				String message = "User does not exist or password is wrong.";
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			}
		} catch (Exception ex) {
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
		}
		
		return messages;
	}
}