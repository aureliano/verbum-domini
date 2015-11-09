package com.github.aureliano.verbum_domini.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.ChapterDao;
import com.github.aureliano.verbum_domini.core.dao.VerseDao;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.BookDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.ChapterDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.VerseDaoImpl;
import com.github.aureliano.verbum_domini.helper.DataHelper;

public class DataBookFacadeTest {

	public DataBookFacadeTest() {
		DataHelper.instance().initializeDataHelpers();
	}
	
	@Test
	public void testSave() throws FileNotFoundException {
		File file = new File("src/test/resources/Luke.json");
		InputStream stream = new FileInputStream(file);
		
		BookBean book = DataBookFacade.save(1, stream);
		
		assertNotNull(book.getId());
		assertEquals("Luke", book.getName());
		
		this.deleteCascadeBook(book);
	}
	
	private void deleteCascadeBook(BookBean book) {
		ChapterDao cdao = new ChapterDaoImpl();
		VerseDao vdao = new VerseDaoImpl();
		
		if (book.getChapters() == null) {
			ChapterBean filter = new ChapterBeanImpl();
			filter.setBook(book);
			
			book.setChapters(cdao.list(filter).getElements());
		}
		
		for (ChapterBean chapter : book.getChapters()) {
			if (chapter.getVerses() == null) {
				VerseBean filter = new VerseBeanImpl();
				filter.setChapter(chapter);
				
				chapter.setVerses(vdao.list(filter).getElements());
			}
			
			for (VerseBean verse : chapter.getVerses()) {
				vdao.delete(verse);
			}
			
			cdao.delete(chapter);
		}
		
		new BookDaoImpl().delete(book);
	}
}