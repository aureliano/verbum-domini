package com.github.aureliano.verbum_domini.helper;

import java.util.Date;

import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.helper.HashHelper;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public class UserDataHelper {

	private UserDataHelper() {
		super();
	}

	public static void createUsers() {
		IDao<UserBean> dao = DaoFactory.createDao(UserBean.class);

		dao.save(prepareUser("santo_antonio"));
		dao.save(prepareUser("santo_agostinho"));
	}
	
	private static UserBeanImpl prepareUser( String login) {
		UserBeanImpl bean = new UserBeanImpl();
		
		bean.setActive(true);
		bean.setCreation(new Date());
		bean.setLastSignIn(new Date());
		bean.setLogin(login);
		bean.setPassword("default_pswd");
		bean.setSaltNumber("12345");
		
		bean.setPassword(HashHelper.generatePasswordHash(bean));
		
		return bean;
	}
}