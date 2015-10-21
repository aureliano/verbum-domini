package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.dao.BookDao;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class BookDaoImplTest {

	private BookDao dao;
	
	public BookDaoImplTest() {
		this.prepareData();
		this.dao = new BookDaoImpl();
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
		assertEquals(new Integer(10), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(4);
		
		assertEquals(7, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		BookBeanImpl filter = new BookBeanImpl();
		assertEquals(new Integer(10), this.dao.list(filter).getSize());
		
		BibleBean bible = new BibleBeanImpl();
		bible.setId(1);
		filter.setBible(bible);
		assertEquals(new Integer(5), this.dao.list(filter).getSize());
		
		bible.setId(9808098);
		assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParamFilter() {
		BookBeanImpl filter = new BookBeanImpl();		
		BibleBean bible = new BibleBeanImpl();
		
		bible.setId(1);
		filter.setBible(bible);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	@Test
	public void testListParams() {
		BookBeanImpl filter = new BookBeanImpl();
		assertEquals(8, this.dao.list(filter, 1, 8).getElements().size());
		
		BibleBean bible = new BibleBeanImpl();
		bible.setId(1);
		filter.setBible(bible);
		assertEquals(4, this.dao.list(filter, 1, 4).getElements().size());
		
		bible.setId(9808098);
		assertEquals(0, this.dao.list(filter, 1, 1).getElements().size());
	}
	
	@Test
	public void testSave() {
		BookBean bean = new BookBeanImpl();
		BibleBean bible = new BibleDaoImpl().get(1);
		
		bean.setBible(bible);
		bean.setName("apocalipse");
		this.dao.save(bean);
		
		BookBean saved = this.dao.load(11);
		this.validateEquals(bean, saved);
		
		bean.setName("Apocalipse");
		saved.setName("Apocalipse");
		this.dao.save(saved);
		
		saved = this.dao.load(11);
		this.validateEquals(bean, saved);
		
		this.dao.delete(saved);
	}
	
	private void validateBean(BookBean bean) {
		assertEquals(new Integer(1), bean.getId());
		assertEquals("Genesis", bean.getName());
		assertEquals(new Integer(1), bean.getBible().getId());
	}
	
	private void validateEquals(BookBean b1, BookBean b2) {
		assertEquals(b1.getBible().getId(), b2.getBible().getId());
		assertEquals(b1, b2);
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}