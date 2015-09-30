package com.github.aureliano.verbum_domini.web.bc;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

public final class SignInBC {

	private SignInBC() {
		super();
	}
	
	public static List<FacesMessage> authenticate(String login, String password) {
		List<FacesMessage> messages = new ArrayList<>();
		
		return messages;
	}
}