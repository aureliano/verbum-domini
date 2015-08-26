package com.github.aureliano.verbum_domini.bean;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.verbum_domini.model.Bible;

public class BibleBeanTest {

	@Test
	public void testToResource() {
		BibleBean bean = new BibleBean();
		
		bean.setCopyright("copyright");
		bean.setEdition("first");
		bean.setEletronicTranscriptionSource("test");
		bean.setEletronicTranscriptionSourceUrl("http://test.com");
		bean.setId(3);
		bean.setLanguage("idiom");
		bean.setName("name");
		bean.setPrintedSource("printed source");
		bean.setUrl("http://test.url.com");
		
		Bible resource = bean.toResource();
		
		Assert.assertEquals(bean.getCopyright(), resource.getCopyright());
		Assert.assertEquals(bean.getEdition(), resource.getEdition());
		Assert.assertEquals(bean.getEletronicTranscriptionSource(), resource.getEletronicTranscriptionSource());
		Assert.assertEquals(bean.getEletronicTranscriptionSourceUrl(), resource.getEletronicTranscriptionSourceUrl());
		Assert.assertEquals(bean.getId(), resource.getBibleId());
		Assert.assertEquals(bean.getLanguage(), resource.getLanguage());
		Assert.assertEquals(bean.getName(), resource.getName());
		Assert.assertEquals(bean.getPrintedSource(), resource.getPrintedSource());
		Assert.assertEquals(bean.getUrl(), resource.getUrl());
	}

	@Test
	public void testHashCode() {
		BibleBean b1 = new BibleBean();
		BibleBean b2 = new BibleBean();
		BibleBean b3 = new BibleBean();
		
		b1.setId(25);
		b2.setId(25);
		b1.setLanguage("latin");
		b2.setLanguage("latin");
		b1.setName("Nova Vulgata");
		b2.setName("Nova Vulgata");
		
		Assert.assertTrue(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setId(25);
		Assert.assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setLanguage("latin");
		Assert.assertFalse(b1.hashCode() == b3.hashCode());
		
		b3.setName("Nova Vulgata");
		Assert.assertTrue(b1.hashCode() == b3.hashCode());
	}

	@Test
	public void testEquals() {
		BibleBean b1 = new BibleBean();
		Assert.assertFalse(b1.equals(""));
		Assert.assertFalse(b1.equals(new Object()));
		
		Assert.assertTrue(b1.equals(b1));
		
		BibleBean b2 = new BibleBean();
		Assert.assertTrue(b1.equals(b2));
		
		BibleBean b3 = new BibleBean();
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
		
		b1.setName("Nova Vulgata");
		b2.setName(null);
		b3.setName("nova vulgata");
		
		Assert.assertFalse(b1.equals(b2));
		Assert.assertFalse(b1.equals(b3));
		
		b2.setName("Nova Vulgata");
		b3.setName("Nova Vulgata");
		
		Assert.assertTrue(b1.equals(b2));
		Assert.assertTrue(b2.equals(b3));
		
		b1.setLanguage("Latin");
		b2.setLanguage(null);
		b3.setLanguage("latin");
		
		Assert.assertFalse(b1.equals(b2));
		Assert.assertFalse(b1.equals(b3));
		
		b2.setLanguage("Latin");
		b3.setLanguage("Latin");
		
		Assert.assertTrue(b1.equals(b2));
		Assert.assertTrue(b2.equals(b3));
	}
}