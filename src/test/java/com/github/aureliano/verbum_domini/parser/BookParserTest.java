package com.github.aureliano.verbum_domini.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.model.Book;

public class BookParserTest {

	@Test
	public void testToResource() {
		BookBean bean = this.createBean();
		Book resource = new BookParser().toResource(bean);
		
		assertEquals(bean.getId(), resource.getBookId());
		assertEquals(bean.getBible().getId(), resource.getBibleId());
		assertEquals(bean.getName(), resource.getName());
	}
	
	private BookBean createBean() {
		BookBean bean = new BookBeanImpl();
		BibleBean bible = new BibleBeanImpl();
		bible.setId(20);
		
		bean.setId(4);
		bean.setBible(bible);
		bean.setName("Book name");
		
		return bean;
	}
}