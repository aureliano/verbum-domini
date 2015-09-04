package com.github.aureliano.verbum_domini.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.relational.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.model.Bible;

public class BibleParserTest {

	@Test
	public void testToResource() {
		BibleBean bean = this.createBean();
		Bible resource = new BibleParser().toResource(bean);
		
		assertEquals(bean.getId(), resource.getBibleId());
		assertEquals(bean.getCopyright(), resource.getCopyright());
		assertEquals(bean.getEdition(), resource.getEdition());
		assertEquals(bean.getEletronicTranscriptionSource(), resource.getEletronicTranscriptionSource());
		assertEquals(bean.getEletronicTranscriptionSourceUrl(), resource.getEletronicTranscriptionSourceUrl());
		assertEquals(bean.getLanguage(), resource.getLanguage());
		assertEquals(bean.getName(), resource.getName());
		assertEquals(bean.getPrintedSource(), resource.getPrintedSource());
		assertEquals(bean.getUrl(), resource.getUrl());
	}
	
	private BibleBean createBean() {
		BibleBean bean = new BibleBeanImpl();
		
		bean.setId(4);
		bean.setCopyright("copyright");
		bean.setEdition("edition");
		bean.setEletronicTranscriptionSource("transc source");
		bean.setEletronicTranscriptionSourceUrl("transc source url");
		bean.setLanguage("language");
		bean.setName("Bible name");
		bean.setPrintedSource("printed source");
		bean.setUrl("url");
		
		return bean;
	}
}