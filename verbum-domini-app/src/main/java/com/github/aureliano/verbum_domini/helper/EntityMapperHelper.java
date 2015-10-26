package com.github.aureliano.verbum_domini.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;

public final class EntityMapperHelper {

	private EntityMapperHelper() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IBean> T map(Class<T> beanType, Map<?, ?> map) {
		if (beanType.isAssignableFrom(BookBean.class)) {
			return (T) mapBook(map);
		} else {
			throw new VerbumDominiException(beanType + " entity mapping not supported.");
		}
	}
	
	private static BookBean mapBook(Map<?, ?> map) {
		BookBean book = new BookBeanImpl();
		book.setName(map.get("book").toString());
		book.setChapters(new ArrayList<ChapterBean>());
		
		map = JsonMapperHelper.getMap(Map.class, map, "chapters");
		for (Object key : map.keySet()) {
			Map<String, Object> data = JsonMapperHelper.getMap(Map.class, map, key);
			Map<String, Object> verses = JsonMapperHelper.getMap(Map.class, data, "verses");
			Map<String, Object> annotations = JsonMapperHelper.getMap(Map.class, data, "annotations");
			
			ChapterBean chapter = new ChapterBeanImpl();
			chapter.setBook(book);
			chapter.setNumber(key.toString());
			chapter.setAnnotations(mapAnnotations(chapter, annotations));
			chapter.setVerses(mapVerses(chapter, verses));
			
			book.getChapters().add(chapter);
		}
		
		return book;
	}
	
	private static List<AnnotationBean> mapAnnotations(ChapterBean chapter, Map<String, Object> map) {
		List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();
		
		if (map == null) {
			return annotations;
		}
		
		for (String ka : map.keySet()) {
			AnnotationBean annotation = new AnnotationBeanImpl();
			annotation.setNumber(ka);
			annotation.setText(map.get(ka).toString());
			
			annotation.setChapter(chapter);
			annotations.add(annotation);
		}
		
		return annotations;
	}
	
	private static List<VerseBean> mapVerses(ChapterBean chapter, Map<String, Object> map) {
		List<VerseBean> verses = new ArrayList<VerseBean>();
		
		for (String kv : map.keySet()) {
			Object o = map.get(kv);
			VerseBean verse = new VerseBeanImpl();
			verse.setNumber(kv);
			
			if (o instanceof Map) {
				String text = ((Map<?, ?>) o).get("verse").toString();
				verse.setText(text);
			} else {
				verse.setText(o.toString());
			}
			
			verse.setChapter(chapter);
			verses.add(verse);
		}
		
		return verses;
	}
}