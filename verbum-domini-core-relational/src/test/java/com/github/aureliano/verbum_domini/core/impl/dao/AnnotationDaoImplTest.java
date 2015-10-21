package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class AnnotationDaoImplTest {

	private AnnotationDao dao;
	
	public AnnotationDaoImplTest() {
		this.prepareData();
		this.dao = new AnnotationDaoImpl();
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
		assertEquals(new Integer(250), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(231);
		
		assertEquals(20, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		AnnotationBean filter = new AnnotationBeanImpl();
		assertEquals(new Integer(250), this.dao.list(filter).getSize());
		
		ChapterBean chapter = new ChapterBeanImpl();
		chapter.setId(1);
		filter.setChapter(chapter);
		assertEquals(new Integer(5), this.dao.list(filter).getSize());
		
		chapter.setId(9808098);
		assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParamFilter() {
		AnnotationBean filter = new AnnotationBeanImpl();		
		ChapterBean chapter = new ChapterBeanImpl();
		
		chapter.setId(1);
		filter.setChapter(chapter);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	@Test
	public void testListParams() {
		AnnotationBean filter = new AnnotationBeanImpl();
		assertEquals(10, this.dao.list(filter, 3, 10).getElements().size());
		
		ChapterBean chapter = new ChapterBeanImpl();
		chapter.setId(1);
		filter.setChapter(chapter);
		assertEquals(4, this.dao.list(filter, 1, 4).getElements().size());
		
		chapter.setId(9808098);
		assertEquals(0, this.dao.list(filter, 1, 1).getElements().size());
	}
	
	@Test
	public void testSave() {
		ChapterBean chapter = new ChapterDaoImpl().get(1);
		AnnotationBean bean = new AnnotationBeanImpl();
		
		bean.setChapter(chapter);
		bean.setNumber("1984");
		bean.setText("Et verbum caro factum est.");
		
		this.dao.save(bean);
		
		AnnotationBean saved = this.dao.load(251);
		this.validateEquals(bean, saved);
		
		bean.setNumber("007");
		saved.setNumber("007");
		this.dao.save(saved);
		
		saved = this.dao.load(251);
		this.validateEquals(bean, saved);
		
		this.dao.delete(saved);
	}
	
	private void validateBean(AnnotationBean bean) {
		assertEquals(new Integer(1), bean.getId());
		assertEquals("1", bean.getNumber());
		assertEquals("Something 1", bean.getText());
		assertEquals(new Integer(1), bean.getChapter().getId());
	}
	
	private void validateEquals(AnnotationBean b1, AnnotationBean b2) {
		assertEquals(b1.getChapter().getId(), b2.getChapter().getId());
		assertEquals(b1.getId(), b2.getId());
		assertEquals(b1.getNumber(), b2.getNumber());
		assertEquals(b1.getText(), b2.getText());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}