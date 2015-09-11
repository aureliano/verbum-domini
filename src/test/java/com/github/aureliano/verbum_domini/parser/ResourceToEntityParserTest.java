package com.github.aureliano.verbum_domini.parser;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Verse;

public class ResourceToEntityParserTest {

	@Test
	public void testParse() {
		assertNull(ResourceToEntityParser.parse(null, null));
		assertNull(ResourceToEntityParser.parse(Object.class, null));
		
		assertTrue(ResourceToEntityParser.parse(Verse.class, new VerseBeanImpl()) instanceof Verse);
		assertTrue(ResourceToEntityParser.parse(Annotation.class, new AnnotationBeanImpl()) instanceof Annotation);
		assertTrue(ResourceToEntityParser.parse(Chapter.class, new ChapterBeanImpl()) instanceof Chapter);
		assertTrue(ResourceToEntityParser.parse(Book.class, new BookBeanImpl()) instanceof Book);
		assertTrue(ResourceToEntityParser.parse(Bible.class, new BibleBeanImpl()) instanceof Bible);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testParseUnsupportedBeanType() {
		ResourceToEntityParser.parse(CustomBean.class, new CustomBean());
	}
	
	private class CustomBean implements IBean {

		private static final long serialVersionUID = 1L;

		@Override
		public Integer getId() {
			return null;
		}

		@Override
		public void setId(Integer arg0) {}
	}
}