package com.github.aureliano.verbum_domini.domain.dao;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.verbum_domini.domain.bean.VerseBean;
import com.github.aureliano.verbum_domini.helper.DataHelper;

public class VerseDaoTest {

	private VerseDao dao;
	
	public VerseDaoTest() {
		this.prepareData();
		this.dao = new VerseDao();
	}
	
	@Test
	public void testGet() {
		VerseBean bean = this.dao.get(1);
		Assert.assertEquals(new Integer(1), bean.getId());
		Assert.assertEquals("1", bean.getNumber());
		Assert.assertEquals(new Integer(1), bean.getChapter().getId());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}