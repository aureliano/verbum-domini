package com.github.aureliano.verbum_domini.core.impl.data;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.core.dao.ChapterDao;
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.AnnotationDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.BibleDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.BookDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.ChapterDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.VerseDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;

public class DataBookBucketImplTest {

	private DataBookBucketImpl bucket;
	private short chaptersSize;
	private short versesSize;
	private short annotationsSize;
	
	public DataBookBucketImplTest() {
		this.bucket = new DataBookBucketImpl();
		this.chaptersSize = 2;
		this.versesSize = 5;
		this.annotationsSize = 2;
		this.prepareData();
	}
	
	@Test
	public void testSaveBatch() {
		BibleBean bible = new BibleDaoImpl().load(1);
		BookBean book = this.prepareBook(bible);
		
		Serializable id = this.bucket.saveBatch(book);
		BookBean entity = new BookDaoImpl().load(id);
		
		assertEquals(this.chaptersSize, entity.getChapters().size());
		this.validateEquals(book, entity);
		
		this.deleteCascadeBook(entity);
	}
	
	private void deleteCascadeBook(BookBean book) {
		ChapterDao cdao = new ChapterDaoImpl();
		VerseDao vdao = new VerseDaoImpl();
		AnnotationDao adao = new AnnotationDaoImpl();
		
		for (ChapterBean chapter : book.getChapters()) {
			for (VerseBean verse : chapter.getVerses()) {
				vdao.delete(verse);
			}
			
			for (AnnotationBean annotation : chapter.getAnnotations()) {
				adao.delete(annotation);
			}
			
			cdao.delete(chapter);
		}
		
		new BookDaoImpl().delete(book);
	}
	
	private BookBean prepareBook(BibleBean bible) {
		BookBean book = new BookBeanImpl();
		
		book.setBible(bible);
		book.setName("Test Book");
		book.setChapters(this.prepareChapters(book));
		
		return book;
	}
	
	private List<ChapterBean> prepareChapters(BookBean book) {
		List<ChapterBean> chapters = new ArrayList<ChapterBean>();
		
		for (short i = 0; i < chaptersSize; i++) {
			ChapterBean chapter = new ChapterBeanImpl();
			
			chapter.setBook(book);
			chapter.setNumber("12345");
			chapter.setVerses(this.prepareVerses(chapter));
			chapter.setAnnotations(this.prepareAnnotations(chapter));
			
			chapters.add(chapter);
		}
		
		return chapters;
	}
	
	private List<VerseBean> prepareVerses(ChapterBean chapter) {
		List<VerseBean> verses = new ArrayList<VerseBean>();
		
		for (short i = 0; i < versesSize; i++) {
			VerseBean verse = new VerseBeanImpl();
			verse.setChapter(chapter);
			verse.setNumber(String.valueOf(i + 1));
			verse.setText("Test verse text " + (i + 1));
			
			verses.add(verse);
		}
		
		return verses;
	}
	
	private List<AnnotationBean> prepareAnnotations(ChapterBean chapter) {
		List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();
		
		for (short i = 0; i < annotationsSize; i++) {
			AnnotationBean annotation = new AnnotationBeanImpl();
			annotation.setChapter(chapter);
			annotation.setNumber(String.valueOf(i + 1));
			annotation.setText("Test annotation text " + (i + 1));
			
			annotations.add(annotation);
		}
		
		return annotations;
	}
	
	private void validateEquals(BookBean b1, BookBean b2) {
		assertEquals(b1.getId(), b2.getId());
		assertEquals(b1.getName(), b2.getName());
		assertEquals(b1.getChapters().size(), b2.getChapters().size());
		
		for (short i = 0; i < b1.getChapters().size(); i++) {
			ChapterBean c1 = b1.getChapters().get(i);
			ChapterBean c2 = b2.getChapters().get(i);
		
			assertEquals(c1.getId(), c2.getId());
			assertEquals(c1.getNumber(), c2.getNumber());
			
			assertEquals(c1.getVerses().size(), c2.getVerses().size());
			assertEquals(c1.getAnnotations().size(), c2.getAnnotations().size());

			assertEquals(this.versesSize, c2.getVerses().size());
			assertEquals(this.annotationsSize, c2.getAnnotations().size());
		
			for (short j = 0; j < c1.getVerses().size(); j++) {
				VerseBean v1 = c1.getVerses().get(j);
				VerseBean v2 = c2.getVerses().get(j);
				
				assertEquals(v1.getNumber(), v2.getNumber());
				assertEquals(v1.getText(), v2.getText());
			}
			
			for (short j = 0; j < c1.getAnnotations().size(); j++) {
				AnnotationBean a1 = c1.getAnnotations().get(j);
				AnnotationBean a2 = c2.getAnnotations().get(j);
				
				assertEquals(a1.getNumber(), a2.getNumber());
				assertEquals(a1.getText(), a2.getText());
			}
		}
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}