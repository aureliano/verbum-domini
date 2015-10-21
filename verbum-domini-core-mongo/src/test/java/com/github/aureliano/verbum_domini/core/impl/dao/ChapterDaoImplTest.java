package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.ChapterDao;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class ChapterDaoImplTest {

	private ChapterDao dao;
	
	public ChapterDaoImplTest() {
		this.prepareData();
		this.dao = new ChapterDaoImpl();
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
		assertEquals(new Integer(50), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(41);
		
		assertEquals(10, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		ChapterBean filter = new ChapterBeanImpl();
		assertEquals(new Integer(50), this.dao.list(filter).getSize());
		
		BookBean book = new BookBeanImpl();
		book.setId(1);
		filter.setBook(book);
		assertEquals(new Integer(5), this.dao.list(filter).getSize());
		
		book.setId(9808098);
		assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParamFilter() {
		ChapterBean filter = new ChapterBeanImpl();		
		BookBean book = new BookBeanImpl();
		
		book.setId(1);
		filter.setBook(book);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	@Test
	public void testListParams() {
		ChapterBean filter = new ChapterBeanImpl();
		assertEquals(10, this.dao.list(filter, 3, 10).getElements().size());
		
		BookBean book = new BookBeanImpl();
		book.setId(1);
		filter.setBook(book);
		assertEquals(4, this.dao.list(filter, 1, 4).getElements().size());
		
		book.setId(9808098);
		assertEquals(0, this.dao.list(filter, 1, 1).getElements().size());
	}
	
	@Test
	public void testSave() {
		BookBean book = new BookDaoImpl().get(1);
		ChapterBean bean = new ChapterBeanImpl();
		
		bean.setBook(book);
		bean.setNumber("1984");
		this.dao.save(bean);
		
		ChapterBean saved = this.dao.load(51);
		this.validateEquals(bean, saved);
		
		bean.setNumber("007");
		saved.setNumber("007");
		this.dao.save(saved);
		
		saved = this.dao.load(51);
		this.validateEquals(bean, saved);
		
		this.dao.delete(saved);
	}
	
	private void validateBean(ChapterBean bean) {
		assertEquals(new Integer(1), bean.getId());
		assertEquals("1", bean.getNumber());
		assertEquals(new Integer(1), bean.getBook().getId());
	}
	
	private void validateEquals(ChapterBean b1, ChapterBean b2) {
		assertEquals(b1.getBook().getId(), b2.getBook().getId());
		assertEquals(b1, b2);
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}