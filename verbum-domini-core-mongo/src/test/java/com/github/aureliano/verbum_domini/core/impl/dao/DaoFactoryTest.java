package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;

public class DaoFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCreateDaoWithUnsupportedType() {
		DaoFactory.createDao(AnnotationBean.class);
	}
	
	public void testCreateDao() {
		IDao<?> dao = DaoFactory.createDao(BibleBean.class);
		assertTrue(dao instanceof BibleDaoImpl);
		dao = DaoFactory.createDao(BibleBeanImpl.class);
		assertTrue(dao instanceof BibleDaoImpl);
		
		dao = DaoFactory.createDao(BookBean.class);
		assertTrue(dao instanceof BookDaoImpl);
		dao = DaoFactory.createDao(BookBeanImpl.class);
		assertTrue(dao instanceof BookDaoImpl);
		
		dao = DaoFactory.createDao(ChapterBean.class);
		assertTrue(dao instanceof ChapterDaoImpl);
		dao = DaoFactory.createDao(ChapterBeanImpl.class);
		assertTrue(dao instanceof ChapterDaoImpl);
		
		dao = DaoFactory.createDao(VerseBean.class);
		assertTrue(dao instanceof VerseDaoImpl);
		dao = DaoFactory.createDao(VerseBeanImpl.class);
		assertTrue(dao instanceof VerseDaoImpl);
		
		dao = DaoFactory.createDao(UserBean.class);
		assertTrue(dao instanceof UserDaoImpl);
		dao = DaoFactory.createDao(UserBeanImpl.class);
		assertTrue(dao instanceof UserDaoImpl);
	}
}