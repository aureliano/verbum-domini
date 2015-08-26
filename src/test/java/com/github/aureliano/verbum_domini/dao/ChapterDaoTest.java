package com.github.aureliano.verbum_domini.dao;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.verbum_domini.bean.BookBean;
import com.github.aureliano.verbum_domini.bean.ChapterBean;
import com.github.aureliano.verbum_domini.dao.helper.DataHelper;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class ChapterDaoTest {

	private ChapterDao dao;
	
	public ChapterDaoTest() {
		this.prepareData();
		this.dao = new ChapterDao();
	}
	
	@Test
	public void testLoad() {
		ChapterBean bean = this.dao.load(1);
		Assert.assertEquals(new Integer(1), bean.getId());
		Assert.assertEquals("1", bean.getNumber());
		Assert.assertEquals("Genesis", bean.getBook().getName());
	}
	
	@Test
	public void testList() {
		Assert.assertEquals(new Integer(50), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(41);
		
		Assert.assertEquals(10, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		ChapterBean filter = new ChapterBean();
		Assert.assertEquals(new Integer(50), this.dao.list(filter).getSize());
		
		BookBean book = new BookBean();
		book.setId(1);
		filter.setBook(book);
		Assert.assertEquals(new Integer(5), this.dao.list(filter).getSize());
		
		book.setId(9808098);
		Assert.assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParamFilter() {
		ChapterBean filter = new ChapterBean();		
		BookBean book = new BookBean();
		
		book.setId(1);
		filter.setBook(book);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		Assert.assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}