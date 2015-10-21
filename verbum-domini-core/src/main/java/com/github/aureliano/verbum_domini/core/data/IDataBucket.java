package com.github.aureliano.verbum_domini.core.data;

import java.io.Serializable;

import com.github.aureliano.verbum_domini.core.bean.IBean;

public interface IDataBucket<T extends IBean> {

	public abstract Serializable saveBatch(T bean);
}