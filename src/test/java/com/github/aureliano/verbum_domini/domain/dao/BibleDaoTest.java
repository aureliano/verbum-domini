package com.github.aureliano.verbum_domini.domain.dao;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.helper.DataHelper;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class BibleDaoTest {
	
	private BibleDao dao;
	
	public BibleDaoTest() {
		this.prepareData();
		this.dao = new BibleDao();
	}
	
	@Test
	public void testGet() {
		BibleBean bean = this.dao.get(1);
		Assert.assertEquals("Libreria Editrice Vaticana", bean.getCopyright());
		Assert.assertEquals("Bibliorum Sacrorum", bean.getEdition());
		Assert.assertEquals("latin", bean.getLanguage());
		Assert.assertEquals("Nova Vulgata", bean.getName());
		Assert.assertEquals("http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html", bean.getUrl());
		Assert.assertEquals(new Integer(1), bean.getId());
		Assert.assertNull(bean.getEletronicTranscriptionSource());
		Assert.assertNull(bean.getEletronicTranscriptionSourceUrl());
		Assert.assertNull(bean.getPrintedSource());
	}
	
	@Test
	public void testList() {
		Assert.assertEquals(new Integer(2), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(2);
		
		Assert.assertEquals(1, this.dao.list(params).getElements().size());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}