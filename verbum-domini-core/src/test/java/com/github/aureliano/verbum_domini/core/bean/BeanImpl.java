package com.github.aureliano.verbum_domini.core.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.aureliano.verbum_domini.core.validation.group.Delete;
import com.github.aureliano.verbum_domini.core.validation.group.Save;

public class BeanImpl implements IBean {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Id is required", groups = { Save.class, Delete.class })
	@Min(value = 1, message = "Id min value is 1", groups = { Save.class })
	@Max(value = 100, message = "Id max value is 100", groups = { Save.class })
	private Integer id;
	
	@Size(min = 3, max = 10, message = "Text must have between 3 and 10 characters", groups= { Save.class })
	private String text;
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}