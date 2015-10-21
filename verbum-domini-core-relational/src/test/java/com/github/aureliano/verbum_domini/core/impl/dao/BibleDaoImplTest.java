package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.dao.BibleDao;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class BibleDaoImplTest {

	private BibleDao dao;
	
	public BibleDaoImplTest() {
		this.prepareData();
		this.dao = new BibleDaoImpl();
	}
	
	@Test
	public void testLoad() {
		this.validateBean(this.dao.load(1));
	}
	
	@Test
	public void testGet() {
		this.validateBean(this.dao.get(1));
	}
	
	@Test
	public void testExist() {
		assertTrue(this.dao.exist(1));
		assertFalse(this.dao.exist(9999999));
	}
	
	@Test
	public void testList() {
		assertEquals(new Integer(2), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(2);
		
		assertEquals(1, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		BibleBean filter = new BibleBeanImpl();
		assertEquals(new Integer(2), this.dao.list(filter).getSize());
		
		filter.setLanguage("english");
		assertEquals(new Integer(1), this.dao.list(filter).getSize());
		
		filter.setLanguage("french");
		assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParams() {
		BibleBean filter = new BibleBeanImpl();
		assertEquals(1, this.dao.list(filter, 1, 1).getElements().size());
		
		filter.setLanguage("english");
		assertEquals(1, this.dao.list(filter, 1, 1).getElements().size());
		
		filter.setLanguage("french");
		assertEquals(0, this.dao.list(filter, 1, 1).getElements().size());
	}
	
	@Test
	public void testSave() {
		BibleBean bean = new BibleBeanImpl();
		
		bean.setCopyright("copyright");
		bean.setEdition("edition");
		bean.setEletronicTranscriptionSource("ets");
		bean.setEletronicTranscriptionSourceUrl("etc url");
		bean.setLanguage("portuguese");
		bean.setName("some name");
		bean.setPrintedSource("printed source");
		bean.setUrl("url");
		
		this.dao.save(bean);
		BibleBean saved = this.dao.load(3);
		this.validateEquals(bean, saved);
		
		bean.setEdition("first edition");
		saved.setEdition("first edition");
		this.dao.save(saved);
		
		saved = this.dao.load(3);
		this.validateEquals(bean, saved);
		
		this.dao.delete(saved);
	}
	
	private void validateBean(BibleBean bean) {
		assertEquals("Libreria Editrice Vaticana", bean.getCopyright());
		assertEquals("Bibliorum Sacrorum", bean.getEdition());
		assertEquals("latin", bean.getLanguage());
		assertEquals("Nova Vulgata", bean.getName());
		assertEquals("http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html", bean.getUrl());
		assertEquals(new Integer(1), bean.getId());
		assertNull(bean.getEletronicTranscriptionSource());
		assertNull(bean.getEletronicTranscriptionSourceUrl());
		assertNull(bean.getPrintedSource());
	}
	
	private void validateEquals(BibleBean b1, BibleBean b2) {
		assertEquals(b1.getCopyright(), b2.getCopyright());
		assertEquals(b1.getEdition(), b2.getEdition());
		assertEquals(b1.getEletronicTranscriptionSource(), b2.getEletronicTranscriptionSource());
		assertEquals(b1.getEletronicTranscriptionSourceUrl(), b2.getEletronicTranscriptionSourceUrl());
		assertEquals(b1.getLanguage(), b2.getLanguage());
		assertEquals(b1.getName(), b2.getName());
		assertEquals(b1.getPrintedSource(), b2.getPrintedSource());
		assertEquals(b1.getUrl(), b2.getUrl());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}