package com.github.aureliano.verbum_domini.bean;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.verbum_domini.model.Book;

public class BookBeanTest {

	@Test
	public void testToResource() {
		BookBean bean = new BookBean();
		BibleBean bible = new BibleBean();
		bible.setId(1);
		
		bean.setBible(bible);
		bean.setId(1);
		bean.setName("name");
		
		Book resource = bean.toResource();
		
		Assert.assertEquals(bean.getId(), resource.getId());
		Assert.assertEquals(bean.getName(), resource.getName());
		Assert.assertEquals(bean.getBible().getId(), resource.getBibleId());
	}
	
	@Test
	public void testHashCode() {
		BookBean b1 = new BookBean();
		BookBean b2 = new BookBean();
		BookBean b3 = new BookBean();
		
		b1.setId(25);
		b2.setId(25);
		b1.setName("Genesis");
		b2.setName("Genesis");
		
		Assert.assertTrue(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setId(25);
		Assert.assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setName("Genesis");
		Assert.assertTrue(b1.hashCode() == b3.hashCode());
	}
	
	@Test
	public void testEquals() {
		BookBean b1 = new BookBean();
		Assert.assertFalse(b1.equals(""));
		Assert.assertFalse(b1.equals(new Object()));
		
		Assert.assertTrue(b1.equals(b1));
		
		BookBean b2 = new BookBean();
		Assert.assertTrue(b1.equals(b2));
		
		BookBean b3 = new BookBean();
		Assert.assertTrue(b2.equals(b3));
		
		b1.setId(1);
		b2.setId(null);
		b3.setId(5);
		
		Assert.assertFalse(b1.equals(b2));
		Assert.assertFalse(b1.equals(b3));
		
		b2.setId(1);
		b3.setId(1);
		
		Assert.assertTrue(b1.equals(b2));
		Assert.assertTrue(b2.equals(b3));
		
		b1.setName("Genesis");
		b2.setName(null);
		b3.setName("genesis");
		
		Assert.assertFalse(b1.equals(b2));
		Assert.assertFalse(b1.equals(b3));
		
		b2.setName("Genesis");
		b3.setName("Genesis");
		
		Assert.assertTrue(b1.equals(b2));
		Assert.assertTrue(b2.equals(b3));
	}
}