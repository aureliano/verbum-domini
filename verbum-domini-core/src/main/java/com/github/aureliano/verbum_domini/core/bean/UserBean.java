package com.github.aureliano.verbum_domini.core.bean;

import java.util.Date;

public interface UserBean extends IBean {

	public abstract void setLogin(String login);
	
	public abstract String getLogin();
	
	public abstract void setPassword(String password);
	
	public abstract String getPassword();
	
	public abstract void setSaltNumber(String saltNumber);
	
	public abstract String getSaltNumber();
	
	public abstract void setCreation(Date creation);
	
	public abstract Date getCreation();
	
	public abstract void setLastSignIn(Date lastSignIn);
	
	public abstract Date getLastSignIn();
	
	public abstract void setActive(Boolean active);
	
	public abstract Boolean isActive();
}