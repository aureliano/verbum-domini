package com.github.aureliano.verbum_domini.core.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.github.aureliano.verbum_domini.core.bean.IBean;

public final class ValidationHelper {

	private ValidationHelper() {
		super();
	}
	
	public static List<String> validate(IBean bean, Class<?>...groups) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<IBean>> validationResult = validator.validate(bean, groups);
		List<String> messages = new ArrayList<>();
		
		if (!validationResult.isEmpty()) {
			for (ConstraintViolation<IBean> fail : validationResult) {
				messages.add(fail.getMessage());
			}
		}
		
		return messages;
	}
}