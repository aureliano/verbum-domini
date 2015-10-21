package com.github.aureliano.verbum_domini.web.bc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.helper.HashHelper;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.UserDaoImpl;

public final class SignInBC {

	private static final Logger logger = Logger.getLogger(SignInBC.class);
	
	private SignInBC() {
		super();
	}
	
	public static List<FacesMessage> authenticate(String login, String password) {
		List<FacesMessage> messages = new ArrayList<>();
		final String signInFailMessage = "User does not exist or password is wrong.";
		
		try {
			UserBean user = findUser(login);
			if (user == null) {
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, signInFailMessage, signInFailMessage));
				
				return messages;
			}
			
			String passwordHash = HashHelper.generatePasswordHash(password, user.getSaltNumber());
			UserDaoImpl dao = new UserDaoImpl();
			boolean authenticated = dao.authenticate(login, passwordHash);

			if (authenticated) {
				user.setLastSignIn(new Date());
				dao.save(user);
			} else {
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, signInFailMessage, signInFailMessage));
			}
		} catch (Exception ex) {
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
			logger.error(ex.getMessage(), ex);
		}
		
		return messages;
	}
	
	private static UserBean findUser(String login) {
		UserBean user = new UserBeanImpl();
		user.setLogin(login);
		List<UserBean> users = new UserDaoImpl().list(user).getElements();
		
		if ((users != null) && (!users.isEmpty())) {
			return users.get(0);
		}
		
		return null;
	}
}