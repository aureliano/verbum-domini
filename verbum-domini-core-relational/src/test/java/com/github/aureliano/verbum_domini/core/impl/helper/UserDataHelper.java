package com.github.aureliano.verbum_domini.core.impl.helper;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;

public final class UserDataHelper {

	private UserDataHelper() {
		super();
	}

	public static void createUsers() {
		Session session = ((PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager()).openSession();
		Transaction transaction = session.beginTransaction();
		
		UserBean santoAgostinho = prepareUser(1, "santo_agostinho");
		session.saveOrUpdate(santoAgostinho);
		
		UserBean santaRita = prepareUser(2, "santa_rita_cassia");
		session.saveOrUpdate(santaRita);
		
		transaction.commit();
	}
	
	private static UserBean prepareUser(Integer id, String login) {
		UserBean bean = new UserBeanImpl();
		
		bean.setId(id);
		bean.setActive(true);
		bean.setCreation(new Date());
		bean.setLastSignIn(new Date());
		bean.setLogin(login);
		bean.setPassword("default_pswd");
		bean.setSaltNumber("12345");
		
		return bean;
	}
}