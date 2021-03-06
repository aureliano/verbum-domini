package com.github.aureliano.verbum_domini.web.listener;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.dao.UserDao;
import com.github.aureliano.verbum_domini.core.helper.HashHelper;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.UserDaoImpl;

public class PrepareUserServletContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(PrepareUserServletContextListener.class);
	private static final String DEFAULT_USER_PASSWORD_KEY = "DEFAULT_USER_PASSWORD";
	
	private UserDao userDao;
	
	public PrepareUserServletContextListener() {
		this.userDao = new UserDaoImpl();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info(" >>> Prepare default users.");
		String password = System.getenv(DEFAULT_USER_PASSWORD_KEY);
		
		if (StringUtils.isEmpty(password)) {
			logger.warn(" Ignoring user saving because no password was found as environment variable " + DEFAULT_USER_PASSWORD_KEY);
			return;
		}
		
		this.saveUser("santo_antonio", password);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) { }
	
	private void saveUser(String login, String password) {
		UserBean user = this.findUserWithLogin(login);
		
		if (user == null) {
			user = this.createUser(login);
		}
		
		user.setPassword(password);
		user.setPassword(HashHelper.generatePasswordHash(user));
		
		this.userDao.save(user);
		logger.info("User " + login + " was saved.");
	}
	
	private UserBean createUser(String login) {
		UserBean user = new UserBeanImpl();
		
		user.setActive(true);
		user.setCreation(new Date());
		user.setLogin(login);
		user.setSaltNumber(HashHelper.generateSaltNumber());
		
		return user;
	}
	
	private UserBean findUserWithLogin(String login) {
		UserBean filter = new UserBeanImpl();
		filter.setLogin(login);
		
		List<UserBean> elements = this.userDao.list(filter).getElements();
		return ((elements.isEmpty()) ? null : elements.get(0));
	}
}