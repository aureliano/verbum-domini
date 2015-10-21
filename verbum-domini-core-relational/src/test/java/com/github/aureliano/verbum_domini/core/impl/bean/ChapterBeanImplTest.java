package com.github.aureliano.verbum_domini.core.impl.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;

public class ChapterBeanImplTest {

	@Test
	public void testHashCode() {
		ChapterBeanImpl b1 = new ChapterBeanImpl();
		ChapterBeanImpl b2 = new ChapterBeanImpl();
		ChapterBeanImpl b3 = new ChapterBeanImpl();
		
		b1.setId(25);
		b2.setId(25);
		b1.setNumber("123");
		b2.setNumber("123");
		
		assertTrue(b1.hashCode() == b2.hashCode());
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setId(25);
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setNumber("123");
		assertTrue(b1.hashCode() == b3.hashCode());
	}

	@Test
	public void testEquals() {
		ChapterBeanImpl b1 = new ChapterBeanImpl();
		assertFalse(b1.equals(""));
		assertFalse(b1.equals(new Object()));
		
		assertTrue(b1.equals(b1));
		
		ChapterBeanImpl b2 = new ChapterBeanImpl();
		assertTrue(b1.equals(b2));
		
		ChapterBeanImpl b3 = new ChapterBeanImpl();
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
		
		b1.setNumber("123");
		b2.setNumber(null);
		b3.setNumber("123");
		
		assertFalse(b1.equals(b2));
		assertTrue(b1.equals(b3));
		
		b2.setNumber("123");
		b3.setNumber("123");
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
	}
}