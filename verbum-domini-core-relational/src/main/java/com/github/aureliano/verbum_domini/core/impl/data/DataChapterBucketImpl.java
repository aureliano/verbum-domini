package com.github.aureliano.verbum_domini.core.impl.data;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.data.DataChapterBucket;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;

public class DataChapterBucketImpl implements DataChapterBucket {
	
	public DataChapterBucketImpl() {
		super();
	}

	@Override
	public Serializable saveBatch(ChapterBean chapter) {
		Session session = ((PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager()).openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			Serializable id = this.saveChapter(chapter, session);
			transaction.commit();
			
			return id;
		} catch (Exception ex) {
			transaction.rollback();
			throw new VerbumDominiException(ex);
		}
	}
	
	public void saveBatch(ChapterBean chapter, Session session) {
		this.saveChapter(chapter, session);
	}
	
	private Serializable saveChapter(ChapterBean chapter, Session session) {
		chapter.setId(this.lastId(session, ChapterBeanImpl.class) + 1);
		Serializable id = session.save(chapter);
		chapter = (ChapterBean) session.load(ChapterBeanImpl.class, id);
		
		this.saveVerses(chapter, session);
		this.saveAnnotations(chapter, session);
		
		return id;
	}
	
	private void saveVerses(ChapterBean chapter, Session session) {
		for (VerseBean verse : chapter.getVerses()) {
			verse.setChapter(chapter);
			
			Integer id = this.lastId(session, VerseBeanImpl.class) + 1;
			verse.setId(id);
			session.save(verse);
		}
	}
	
	private void saveAnnotations(ChapterBean chapter, Session session) {
		for (AnnotationBean annotation : chapter.getAnnotations()) {
			annotation.setChapter(chapter);
			
			Integer id = this.lastId(session, AnnotationBeanImpl.class) + 1;
			annotation.setId(id);
			session.save(annotation);
		}
	}
	
	private Integer lastId(Session session, Class<?> type) {
		Criteria criteria = session.createCriteria(type);
		return (((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
	}
}