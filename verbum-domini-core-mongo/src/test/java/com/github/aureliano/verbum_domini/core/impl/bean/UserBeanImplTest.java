package com.github.aureliano.verbum_domini.core.impl.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserBeanImplTest {

	@Test
	public void testHashCode() {
		UserBeanImpl b1 = new UserBeanImpl();
		UserBeanImpl b2 = new UserBeanImpl();
		UserBeanImpl b3 = new UserBeanImpl();
		
		b1.setId(25);
		b2.setId(25);
		b1.setLogin("123");
		b2.setLogin("123");
		b1.setPassword("pswd");
		b2.setPassword("pswd");
		
		assertTrue(b1.hashCode() == b2.hashCode());
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setId(25);
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setLogin("123");
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setPassword("pswd");
		assertTrue(b1.hashCode() == b3.hashCode());
	}

	@Test
	public void testEquals() {
		UserBeanImpl b1 = new UserBeanImpl();
		assertFalse(b1.equals(""));
		assertFalse(b1.equals(new Object()));
		
		assertTrue(b1.equals(b1));
		
		UserBeanImpl b2 = new UserBeanImpl();
		assertTrue(b1.equals(b2));
		
		UserBeanImpl b3 = new UserBeanImpl();
		assertTrue(b2.equals(b3));
		
		b1.setPassword("pswd");
		b2.setPassword(null);
		b3.setPassword(null);
		
		assertFalse(b1.equals(b2));
		assertFalse(b1.equals(b3));
		
		b2.setPassword("pswd");
		b3.setPassword("pswd");
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
		
		b1.setLogin("123");
		b2.setLogin(null);
		b3.setLogin(null);
		
		assertFalse(b1.equals(b2));
		assertFalse(b1.equals(b3));
		
		b2.setLogin("123");
		b3.setLogin("123");
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
	}
}