package com.github.aureliano.verbum_domini.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;

public class EntityMapperHelperTest {

	@Test
	public void testMap() throws FileNotFoundException {
		File file = new File("src/test/resources/Luke.json");
		InputStream stream = new FileInputStream(file);
		
		Map<String, Object> map = JsonMapperHelper.map(Map.class, stream);
		
		BookBean book = EntityMapperHelper.map(BookBean.class, map);
		assertEquals("Luke", book.getName());
		
		List<ChapterBean> chapters = book.getChapters();
		assertEquals(25, chapters.size());
		
		for (ChapterBean chapter : chapters) {
			assertNotNull(chapter.getBook());
			assertFalse(chapter.getNumber().isEmpty());
			
			for (VerseBean verse : chapter.getVerses()) {
				assertNotNull(verse.getChapter());
				assertFalse(verse.getNumber().isEmpty());
			}
			
			for (AnnotationBean annotation : chapter.getAnnotations()) {
				assertNotNull(annotation.getChapter());
				assertFalse(annotation.getNumber().isEmpty());
				assertFalse(annotation.getText().isEmpty());
			}
		}
	}
}