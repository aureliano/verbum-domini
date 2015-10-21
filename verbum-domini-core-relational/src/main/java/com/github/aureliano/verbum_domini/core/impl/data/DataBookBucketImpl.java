package com.github.aureliano.verbum_domini.core.impl.data;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.data.DataBookBucket;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;

public class DataBookBucketImpl implements DataBookBucket {
	
	private DataChapterBucketImpl dataChapterBucket;
	
	public DataBookBucketImpl() {
		this.dataChapterBucket = new DataChapterBucketImpl();
	}

	@Override
	public Serializable saveBatch(BookBean book) {
		Session session = ((PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager()).openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			Serializable id = this.saveBook(book, session);
			transaction.commit();
			
			return id;
		} catch (Exception ex) {
			transaction.rollback();
			throw new VerbumDominiException(ex);
		}
	}
	
	private Serializable saveBook(BookBean book, Session session) {
		List<ChapterBean> chapters = book.getChapters();
		book.setId(this.lastId(session, BookBeanImpl.class) + 1);
		Serializable id = session.save(book);
		book = (BookBean) session.load(BookBeanImpl.class, id);
		
		book.setChapters(chapters);
		this.saveChapters(book, session);
		
		return id;
	}
	
	private void saveChapters(BookBean book, Session session) {
		for (ChapterBean chapter : book.getChapters()) {
			chapter.setBook(book);
			this.dataChapterBucket.saveBatch(chapter, session);
		}
	}
	
	private Integer lastId(Session session, Class<?> type) {
		Criteria criteria = session.createCriteria(type);
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}