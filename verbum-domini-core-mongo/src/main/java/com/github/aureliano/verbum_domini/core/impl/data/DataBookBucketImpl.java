package com.github.aureliano.verbum_domini.core.impl.data;

import java.io.Serializable;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.BookDao;
import com.github.aureliano.verbum_domini.core.data.DataBookBucket;
import com.github.aureliano.verbum_domini.core.impl.dao.BookDaoImpl;

public class DataBookBucketImpl implements DataBookBucket {

	private DataChapterBucketImpl dataChapterBucket;
	
	public DataBookBucketImpl() {
		this.dataChapterBucket = new DataChapterBucketImpl();
	}

	@Override
	public Serializable saveBatch(BookBean book) {
		return this.saveBook(book);
	}

	private Serializable saveBook(BookBean book) {
		BookDao dao = new BookDaoImpl();
		Serializable id = dao.save(book);
		
		this.saveChapters(book);
		
		return id;
	}
	
	private void saveChapters(BookBean book) {
		for (ChapterBean chapter : book.getChapters()) {
			chapter.setBook(book);
			this.dataChapterBucket.saveBatch(chapter);
		}
	}
}