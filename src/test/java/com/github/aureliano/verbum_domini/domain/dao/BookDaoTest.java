package com.github.aureliano.verbum_domini.domain.dao;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.domain.dao.BookDao;
import com.github.aureliano.verbum_domini.helper.DataHelper;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class BookDaoTest {

	private BookDao dao;
	
	public BookDaoTest() {
		this.prepareData();
		this.dao = new BookDao();
	}
	
	@Test
	public void testLoad() {
		BookBean bean = this.dao.load(1);
		Assert.assertEquals(new Integer(1), bean.getId());
		Assert.assertEquals("Genesis", bean.getName());
		Assert.assertEquals("Nova Vulgata", bean.getBible().getName());
	}
	
	@Test
	public void testList() {
		Assert.assertEquals(new Integer(10), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(4);
		
		Assert.assertEquals(7, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		BookBean filter = new BookBean();
		Assert.assertEquals(new Integer(10), this.dao.list(filter).getSize());
		
		BibleBean bible = new BibleBean();
		bible.setId(1);
		filter.setBible(bible);
		Assert.assertEquals(new Integer(5), this.dao.list(filter).getSize());
		
		bible.setId(9808098);
		Assert.assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParamFilter() {
		BookBean filter = new BookBean();		
		BibleBean bible = new BibleBean();
		
		bible.setId(1);
		filter.setBible(bible);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		Assert.assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}