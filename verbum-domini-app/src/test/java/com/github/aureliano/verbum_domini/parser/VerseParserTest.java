package com.github.aureliano.verbum_domini.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.model.Verse;

public class VerseParserTest {

	@Test
	public void testToResource() {
		VerseBean bean = this.createBean();
		Verse resource = new VerseParser().toResource(bean);
		
		assertEquals(bean.getId(), resource.getVerseId());
		assertEquals(bean.getChapter().getId(), resource.getChapterId());
		assertEquals(bean.getNumber(), resource.getNumber());
		assertEquals(bean.getText(), resource.getText());
		
		assertEquals(1, bean.getAnnotations().size());
		AnnotationBean annotation = bean.getAnnotations().get(0);
		
		assertEquals(new Integer(55), annotation.getId());
		assertEquals("1", annotation.getNumber());
		assertEquals("Something about a verse.", annotation.getText());
	}
	
	private VerseBean createBean() {
		VerseBean bean = new VerseBeanImpl();
		ChapterBean chapter = new ChapterBeanImpl();
		chapter.setId(20);
		
		bean.setId(4);
		bean.setChapter(chapter);
		bean.setNumber("12345");
		bean.setText("This is a verse.");
		
		AnnotationBean a = new AnnotationBeanImpl();
		a.setId(55);
		a.setNumber("1");
		a.setText("Something about a verse.");
		
		bean.addAnnotation(a);
		
		return bean;
	}
}