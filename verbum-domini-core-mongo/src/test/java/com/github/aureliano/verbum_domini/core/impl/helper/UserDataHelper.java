package com.github.aureliano.verbum_domini.core.impl.helper;

import java.util.Date;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.mongodb.client.MongoCollection;

public class UserDataHelper {
	
	private UserDataHelper() {
		super();
	}

	public static void createUsers() {
		AppConfiguration config = AppConfiguration.instance();
		PersistenceManagerImpl persistenceManager = (PersistenceManagerImpl) config.getPersistenceManager();
		MongoCollection<UserBeanImpl> coll = persistenceManager.fetchCollection(UserBeanImpl.class);
		
		UserBeanImpl santoAgostinho = prepareUser(1, "santo_agostinho");
		coll.insertOne(santoAgostinho);
		
		UserBeanImpl santaRita = prepareUser(2, "santa_rita_cassia");
		coll.insertOne(santaRita);
	}
	
	private static UserBeanImpl prepareUser(Integer id, String login) {
		UserBeanImpl bean = new UserBeanImpl();
		
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