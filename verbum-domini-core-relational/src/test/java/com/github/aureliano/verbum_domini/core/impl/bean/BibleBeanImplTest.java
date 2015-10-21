package com.github.aureliano.verbum_domini.core.impl.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;

public class BibleBeanImplTest {

	@Test
	public void testHashCode() {
		BibleBeanImpl b1 = new BibleBeanImpl();
		BibleBeanImpl b2 = new BibleBeanImpl();
		BibleBeanImpl b3 = new BibleBeanImpl();
		
		b1.setId(25);
		b2.setId(25);
		b1.setLanguage("latin");
		b2.setLanguage("latin");
		b1.setName("Nova Vulgata");
		b2.setName("Nova Vulgata");
		
		assertTrue(b1.hashCode() == b2.hashCode());
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setId(25);
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setLanguage("latin");
		assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setName("Nova Vulgata");
		assertTrue(b1.hashCode() == b3.hashCode());
	}

	@Test
	public void testEquals() {
		BibleBeanImpl b1 = new BibleBeanImpl();
		assertFalse(b1.equals(""));
		assertFalse(b1.equals(new Object()));
		
		assertTrue(b1.equals(b1));
		
		BibleBeanImpl b2 = new BibleBeanImpl();
		assertTrue(b1.equals(b2));
		
		BibleBeanImpl b3 = new BibleBeanImpl();
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
		
		b1.setName("Nova Vulgata");
		b2.setName(null);
		b3.setName("nova vulgata");
		
		assertFalse(b1.equals(b2));
		assertFalse(b1.equals(b3));
		
		b2.setName("Nova Vulgata");
		b3.setName("Nova Vulgata");
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
		
		b1.setLanguage("Latin");
		b2.setLanguage(null);
		b3.setLanguage("latin");
		
		assertFalse(b1.equals(b2));
		assertFalse(b1.equals(b3));
		
		b2.setLanguage("Latin");
		b3.setLanguage("Latin");
		
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b3));
	}
}