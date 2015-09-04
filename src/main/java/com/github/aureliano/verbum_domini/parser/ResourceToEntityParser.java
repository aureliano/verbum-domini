package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;

public final class ResourceToEntityParser {

	private static VerseParser verseParser;
	private static AnnotationParser annotationParser;
	private static ChapterParser chapterParser;
	private static BookParser bookParser;
	private static BibleParser bibleParser;
	
	private ResourceToEntityParser() {
		super();
	}
	
	public static <T> T parse(Class<T> type, IBean bean) {
		if (bean == null) {
			return null;
		} else if (VerseBean.class.isAssignableFrom(bean.getClass())) {
			return (T) createVerseParser().toResource((VerseBean) bean);
		} else if (AnnotationBean.class.isAssignableFrom(bean.getClass())) {
			return (T) createAnnotationParser().toResource((AnnotationBean) bean);
		} else if (ChapterBean.class.isAssignableFrom(bean.getClass())) {
			return (T) createChapterParser().toResource((ChapterBean) bean);
		} else if (BookBean.class.isAssignableFrom(bean.getClass())) {
			return (T) createBookParser().toResource((BookBean) bean);
		} else if (BibleBean.class.isAssignableFrom(bean.getClass())) {
			return (T) createBibleParser().toResource((BibleBean) bean);
		}
		
		throw new UnsupportedOperationException("There is no parser to bean type " + bean.getClass().getName());
	}
	
	private static VerseParser createVerseParser() {
		if (verseParser == null) {
			verseParser = new VerseParser();
		}
		
		return verseParser;
	}
	
	private static AnnotationParser createAnnotationParser() {
		if (annotationParser == null) {
			annotationParser = new AnnotationParser();
		}
		
		return annotationParser;
	}
	
	private static ChapterParser createChapterParser() {
		if (chapterParser == null) {
			chapterParser = new ChapterParser();
		}
		
		return chapterParser;
	}
	
	private static BookParser createBookParser() {
		if (bookParser == null) {
			bookParser = new BookParser();
		}
		
		return bookParser;
	}
	
	private static BibleParser createBibleParser() {
		if (bibleParser == null) {
			bibleParser = new BibleParser();
		}
		
		return bibleParser;
	}
}