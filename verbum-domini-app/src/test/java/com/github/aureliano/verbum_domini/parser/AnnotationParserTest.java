package com.github.aureliano.verbum_domini.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.model.Annotation;

public class AnnotationParserTest {

	@Test
	public void testToResource() {
		AnnotationBean bean = this.createBean();
		Annotation resource = new AnnotationParser().toResource(bean);
		
		assertEquals(bean.getId(), resource.getAnnotationId());
		assertEquals(bean.getNumber(), resource.getNumber());
		assertEquals(bean.getText(), resource.getText());
	}
	
	private AnnotationBean createBean() {
		AnnotationBean bean = new AnnotationBeanImpl();
		ChapterBean chapter = new ChapterBeanImpl();
		chapter.setId(20);
		
		bean.setId(4);
		bean.setNumber("12345");
		bean.setText("This is an annotation.");
		
		return bean;
	}
}