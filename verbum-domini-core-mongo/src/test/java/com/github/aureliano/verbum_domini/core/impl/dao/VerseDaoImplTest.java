package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class VerseDaoImplTest {

	private VerseDao dao;
	
	public VerseDaoImplTest() {
		this.prepareData();
		this.dao = new VerseDaoImpl();
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
		VerseBean filter = new VerseBeanImpl();
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
		VerseBean filter = new VerseBeanImpl();		
		ChapterBean chapter = new ChapterBeanImpl();
		
		chapter.setId(1);
		filter.setChapter(chapter);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	@Test
	public void testListParams() {
		VerseBean filter = new VerseBeanImpl();
		assertEquals(10, this.dao.list(filter, 5, 10).getElements().size());
		
		ChapterBean chapter = new ChapterBeanImpl();
		chapter.setId(1);
		filter.setChapter(chapter);
		assertEquals(4, this.dao.list(filter, 1, 4).getElements().size());
		
		chapter.setId(9808098);
		assertEquals(0, this.dao.list(filter, 1, 10).getElements().size());
	}
	
	@Test
	public void testSave() {
		ChapterBean chapter = new ChapterDaoImpl().get(1);
		VerseBean bean = new VerseBeanImpl();
		
		bean.setChapter(chapter);
		bean.setNumber("1984");
		bean.setText("Et verbum caro factum est.");
		this.dao.save(bean);
		
		VerseBean saved = this.dao.load(251);
		this.validateEquals(bean, saved);
		
		bean.setNumber("007");
		saved.setNumber("007");
		this.dao.save(saved);
		
		saved = this.dao.load(251);
		this.validateEquals(bean, saved);
		
		this.dao.delete(saved);
	}
	
	private void validateBean(VerseBean bean) {
		assertEquals(new Integer(1), bean.getId());
		assertEquals("1", bean.getNumber());
		assertEquals("Something 1", bean.getText());
		assertEquals(new Integer(1), bean.getChapter().getId());
	}
	
	private void validateEquals(VerseBean b1, VerseBean b2) {
		assertEquals(b1.getChapter().getId(), b2.getChapter().getId());
		assertEquals(b1, b2);
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}