package com.github.aureliano.verbum_domini.core.impl.data;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.AnnotationDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.BookDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.ChapterDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.VerseDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;

public class DataChapterBucketImplTest {

	private DataChapterBucketImpl bucket;
	private short versesSize;
	private short annotationsSize;
	
	public DataChapterBucketImplTest() {
		this.bucket = new DataChapterBucketImpl();
		this.versesSize = 5;
		this.annotationsSize = 2;
		this.prepareData();
	}
	
	@Test
	public void testSaveBatch() {
		BookBean book = new BookDaoImpl().load(1);
		ChapterBean chapter = this.prepareChapter(book);
		
		Serializable id = this.bucket.saveBatch(chapter);
		
		ChapterBean entity = new ChapterDaoImpl().load(id);
		this.validateEquals(chapter, entity);
		
		assertEquals(this.versesSize, entity.getVerses().size());
		assertEquals(this.annotationsSize, entity.getAnnotations().size());
		
		this.deleteCascadeChapter(entity);
	}
	
	private void deleteCascadeChapter(ChapterBean chapter) {
		VerseDao vdao = new VerseDaoImpl();
		AnnotationDao adao = new AnnotationDaoImpl();
		
		for (VerseBean verse : chapter.getVerses()) {
			vdao.delete(verse);
		}
		
		for (AnnotationBean annotation : chapter.getAnnotations()) {
			adao.delete(annotation);
		}
		
		new ChapterDaoImpl().delete(chapter);
	}
	
	private ChapterBean prepareChapter(BookBean book) {
		ChapterBean chapter = new ChapterBeanImpl();
		
		chapter.setBook(book);
		chapter.setNumber("12345");
		chapter.setVerses(this.prepareVerses(chapter));
		chapter.setAnnotations(this.prepareAnnotations(chapter));
		
		return chapter;
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
	
	private void validateEquals(ChapterBean b1, ChapterBean b2) {
		assertEquals(b1.getId(), b2.getId());
		assertEquals(b1.getNumber(), b2.getNumber());
		assertEquals(b1.getVerses().size(), b2.getVerses().size());
		assertEquals(b1.getAnnotations().size(), b2.getAnnotations().size());
		
		for (short i = 0; i < b1.getVerses().size(); i++) {
			assertEquals(b1.getVerses().get(i).getNumber(), b2.getVerses().get(i).getNumber());
			assertEquals(b1.getVerses().get(i).getText(), b2.getVerses().get(i).getText());
		}
		
		for (short i = 0; i < b1.getAnnotations().size(); i++) {
			assertEquals(b1.getAnnotations().get(i).getNumber(), b2.getAnnotations().get(i).getNumber());
			assertEquals(b1.getAnnotations().get(i).getText(), b2.getAnnotations().get(i).getText());
		}
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}