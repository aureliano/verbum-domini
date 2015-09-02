package com.github.aureliano.verbum_domini.domain.dao;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.verbum_domini.domain.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.domain.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.helper.DataHelper;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class AnnotationDaoTest {

	private AnnotationDao dao;
	
	public AnnotationDaoTest() {
		this.prepareData();
		this.dao = new AnnotationDao();
	}
	
	@Test
	public void testGet() {
		AnnotationBean bean = this.dao.get(1);
		Assert.assertEquals(new Integer(1), bean.getId());
		Assert.assertEquals("1", bean.getNumber());
		Assert.assertEquals("Something 1", bean.getText());
		Assert.assertEquals(new Integer(1), bean.getChapter().getId());
	}
	
	@Test
	public void testList() {
		Assert.assertEquals(new Integer(250), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(231);
		
		Assert.assertEquals(20, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		AnnotationBean filter = new AnnotationBean();
		Assert.assertEquals(new Integer(250), this.dao.list(filter).getSize());
		
		ChapterBean chapter = new ChapterBean();
		chapter.setId(1);
		filter.setChapter(chapter);
		Assert.assertEquals(new Integer(5), this.dao.list(filter).getSize());
		
		chapter.setId(9808098);
		Assert.assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParamFilter() {
		AnnotationBean filter = new AnnotationBean();		
		ChapterBean chapter = new ChapterBean();
		
		chapter.setId(1);
		filter.setChapter(chapter);
		ServiceParams params = new ServiceParams().withPages(1).withStart(2);
		
		Assert.assertEquals(4, this.dao.list(filter, params).getElements().size());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}