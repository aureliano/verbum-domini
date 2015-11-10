package com.github.aureliano.verbum_domini.core.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BeanImpl;
import com.github.aureliano.verbum_domini.core.validation.group.Delete;
import com.github.aureliano.verbum_domini.core.validation.group.Save;

public class ValidationHelperTest {

	@Test
	public void testValidate() {
		BeanImpl b = new BeanImpl();
		List<String> messages = null;
		
		messages = ValidationHelper.validate(b, new Class<?>[0]);
		assertTrue(messages.isEmpty());
		
		messages = ValidationHelper.validate(b, Delete.class);
		assertEquals(1, messages.size());
		assertEquals("Id is required", messages.get(0));
		
		b.setId(0);
		messages = ValidationHelper.validate(b, Save.class);
		assertEquals(1, messages.size());
		assertEquals("Id min value is 1", messages.get(0));
		
		b.setId(101);
		messages = ValidationHelper.validate(b, Save.class);
		assertEquals(1, messages.size());
		assertEquals("Id max value is 100", messages.get(0));
		
		b.setId(1);
		messages = ValidationHelper.validate(b, Save.class);
		assertTrue(messages.isEmpty());
		
		b.setText("");
		messages = ValidationHelper.validate(b, Save.class);
		assertEquals(1, messages.size());
		assertEquals("Text must have between 3 and 10 characters", messages.get(0));
		
		b.setText("ab");
		messages = ValidationHelper.validate(b, Save.class);
		assertEquals(1, messages.size());
		assertEquals("Text must have between 3 and 10 characters", messages.get(0));
		
		b.setText("01234567891");
		messages = ValidationHelper.validate(b, Save.class);
		assertEquals(1, messages.size());
		assertEquals("Text must have between 3 and 10 characters", messages.get(0));
	}
}