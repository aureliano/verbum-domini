package com.github.aureliano.verbum_domini.core.dao;

import com.github.aureliano.verbum_domini.core.bean.UserBean;

public interface UserDao extends IDao<UserBean> {

	public abstract boolean authenticate(String login, String password);
}