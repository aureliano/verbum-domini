package com.github.aureliano.verbum_domini.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.model.Chapter;

public class ChapterParserTest {

	@Test
	public void testToResource() {
		ChapterBean bean = this.createBean();
		Chapter resource = new ChapterParser().toResource(bean);
		
		assertEquals(bean.getId(), resource.getChapterId());
		assertEquals(bean.getBook().getId(), resource.getBookId());
		assertEquals(bean.getNumber(), resource.getNumber());
	}
	
	private ChapterBean createBean() {
		ChapterBean bean = new ChapterBeanImpl();
		BookBean book = new BookBeanImpl();
		book.setId(20);
		
		bean.setId(4);
		bean.setBook(book);
		bean.setNumber("12345");
		
		return bean;
	}
}