package com.github.aureliano.verbum_domini.core.impl.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;

public class BookBeanImplTest {

	@Test
	public void testHashCode() {
		BookBeanImpl b1 = new BookBeanImpl();
		BookBeanImpl b2 = new BookBeanImpl();
		BookBeanImpl b3 = new BookBeanImpl();
		
		b1.setId(25);
		b2.setId(25);
		b1.setName("Genesis");
		b2.setName("Genesis");
		
		assertTrue(b1.hashCode() == b2.hashCode());
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setId(25);
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setName("Genesis");
		assertTrue(b1.hashCode() == b3.hashCode());
	}
	
	@Test
	public void testEquals() {
		BookBeanImpl b1 = new BookBeanImpl();
		assertFalse(b1.equals(""));
		assertFalse(b1.equals(new Object()));
		
		assertTrue(b1.equals(b1));
		
		BookBeanImpl b2 = new BookBeanImpl();
		assertTrue(b1.equals(b2));
		
		BookBeanImpl b3 = new BookBeanImpl();
		assertTrue(b2.equals(b3));
		
		b1.setId(1);
		b2.setId(null);
		b3.setId(5);
		
		assertFalse(b1.equals(b2));
		assertFalse(b1.equals(b3));
		
		b2.setId(1);
		b3.setId(1);
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
		
		b1.setName("Genesis");
		b2.setName(null);
		b3.setName("genesis");
		
		assertFalse(b1.equals(b2));
		assertFalse(b1.equals(b3));
		
		b2.setName("Genesis");
		b3.setName("Genesis");
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
	}
}