package com.github.aureliano.verbum_domini.domain.bean;

import java.io.Serializable;

public interface IBean extends Serializable {

	public abstract <T extends Object> T toResource();
}