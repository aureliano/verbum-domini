package com.github.aureliano.verbum_domini.core.impl.data;

import java.io.Serializable;

import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.ChapterDao;
import com.github.aureliano.verbum_domini.core.data.DataChapterBucket;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.ChapterDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;
import com.mongodb.client.MongoCollection;

public class DataChapterBucketImpl implements DataChapterBucket {

	private PersistenceManagerImpl persistenceManager;
	
	public DataChapterBucketImpl() {
		this.persistenceManager = DaoHelper.getPersistenceManager();
	}

	@Override
	public Serializable saveBatch(ChapterBean chapter) {
		return this.saveChapter(chapter);
	}
	
	private Serializable saveChapter(ChapterBean chapter) {
		ChapterDao dao = new ChapterDaoImpl();
		
		Serializable id = dao.save(chapter);
		
		this.saveVerses(chapter);
		
		return id;
	}
	
	private void saveVerses(ChapterBean chapter) {
		MongoCollection<VerseBeanImpl> coll = this.persistenceManager.fetchCollection(VerseBeanImpl.class);
		
		for (VerseBean verse : chapter.getVerses()) {
			verse.setChapter(chapter);
			
			Integer id = DaoHelper.nextId(verse);
			verse.setId(id);
			coll.insertOne((VerseBeanImpl) verse);
		}
	}
}