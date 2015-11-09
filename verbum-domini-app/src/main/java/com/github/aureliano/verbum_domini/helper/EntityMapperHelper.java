package com.github.aureliano.verbum_domini.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
			chapter.setVerses(mapVerses(chapter, verses, annotations));
			
			book.getChapters().add(chapter);
		}
		
		return book;
	}
	
	private static List<VerseBean> mapVerses(
			ChapterBean chapter, Map<String, Object> mapVerses, Map<String, Object> mapAnnotations) {
		
		Map<String, AnnotationBean> hash = buildAnnotationsHash(mapAnnotations);
		
		List<VerseBean> verses = new ArrayList<VerseBean>();
		
		for (String kv : mapVerses.keySet()) {
			Object o = mapVerses.get(kv);
			VerseBean verse = new VerseBeanImpl();
			verse.setNumber(kv);
			
			if (o instanceof Map) {
				String text = ((Map<?, ?>) o).get("verse").toString();
				verse.setText(text);
				
				List<String> annotations = (List<String>) ((Map<?, ?>) o).get("annotations");
				for (String key : annotations) {
					AnnotationBean annotation = hash.get(key);
					if (annotation == null) {
						throw new VerbumDominiException("Could not find any annotation bean to the verse " + kv + " on chapter " + chapter.getNumber());
					}
					verse.addAnnotation(annotation);
				}
			} else {
				verse.setText(o.toString());
			}
			
			verse.setChapter(chapter);
			verses.add(verse);
		}
		
		return verses;
	}
	
	private static Map<String, AnnotationBean> buildAnnotationsHash(Map<String, Object> mapAnnotations) {
		Map<String, AnnotationBean> hash = new HashMap<String, AnnotationBean>();
		
		for (String kv : mapAnnotations.keySet()) {
			AnnotationBean annotation = new AnnotationBeanImpl();
			
			String text = mapAnnotations.get(kv).toString();
			String verseRange = RegexHelper.scan("^\\[[^\\]]+\\]", text);
			if (!StringUtils.isEmpty(verseRange)) {
				text.replaceFirst("^\\[[^\\]]+\\]", verseRange.replaceAll("\\s+", ""));
			}
			
			annotation.setNumber(kv);
			annotation.setText(text);
			
			hash.put(kv, annotation);
		}
		
		return hash;
	}
}