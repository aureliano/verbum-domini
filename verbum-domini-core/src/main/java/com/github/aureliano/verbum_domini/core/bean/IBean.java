package com.github.aureliano.verbum_domini.core.bean;

import java.io.Serializable;

public interface IBean extends Serializable {

	public abstract void setId(Integer id);
	
	public abstract Integer getId();
}