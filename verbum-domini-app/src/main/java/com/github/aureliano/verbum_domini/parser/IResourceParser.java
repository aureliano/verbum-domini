package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.IBean;

public interface IResourceParser<T extends IBean> {

	public abstract Object toResource(T bean);
}