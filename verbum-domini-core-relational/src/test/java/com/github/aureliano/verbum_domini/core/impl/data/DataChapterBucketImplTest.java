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
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
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
		
		VerseBean verse = new VerseBeanImpl();
		verse.setChapter(entity);
		entity.setVerses(new VerseDaoImpl().list(verse).getElements());
		
		this.validateEquals(chapter, entity);
		
		assertEquals(this.versesSize, entity.getVerses().size());
		
		this.deleteCascadeChapter(entity);
	}
	
	private void deleteCascadeChapter(ChapterBean chapter) {
		VerseDao vdao = new VerseDaoImpl();
		
		for (VerseBean verse : chapter.getVerses()) {
			vdao.delete(verse);
		}
		
		new ChapterDaoImpl().delete(chapter);
	}
	
	private ChapterBean prepareChapter(BookBean book) {
		ChapterBean chapter = new ChapterBeanImpl();
		
		chapter.setBook(book);
		chapter.setNumber("12345");
		
		List<VerseBean> verses = this.prepareVerses(chapter);
		for (VerseBean verse : verses) {
			verse.setAnnotations(this.prepareAnnotations());
		}
		
		chapter.setVerses(this.prepareVerses(chapter));
		
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
	
	private List<AnnotationBean> prepareAnnotations() {
		List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();
		
		for (short i = 0; i < annotationsSize; i++) {
			AnnotationBean annotation = new AnnotationBeanImpl();
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
		
		for (short i = 0; i < b1.getVerses().size(); i++) {
			VerseBean v1 = b1.getVerses().get(i);
			VerseBean v2 = b2.getVerses().get(i);
			
			assertEquals(v1.getNumber(), v2.getNumber());
			assertEquals(v1.getId(), v2.getId());
			
			if (v1.getAnnotations() != null) {
				assertEquals(v1.getAnnotations().size(), v2.getAnnotations().size());
			}
		}
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}