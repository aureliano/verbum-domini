package com.github.aureliano.verbum_domini.web.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.IBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.BibleDao;
import com.github.aureliano.verbum_domini.core.data.DataBookBucket;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.BibleDaoImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.impl.data.DataBookBucketImpl;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.helper.EntityMapperHelper;
import com.github.aureliano.verbum_domini.helper.JsonMapperHelper;

public class DataSeedServletContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(DataSeedServletContextListener.class);
	private static final String DATA_PATH = "/data/bible/";
	
	private DataBookBucket dataBookBucket;
	private BibleDao bibleDao;
	
	public DataSeedServletContextListener() {
		this.dataBookBucket = new DataBookBucketImpl();
		this.bibleDao = new BibleDaoImpl();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Load bible data to database.");
		File[] biblesDir = this.getDataDir().listFiles();
		this.dropEntities();
		
		for (File dir : biblesDir) {
			logger.info("Save data coming from: " + dir.getPath());
			this.saveDataToDatabase(dir);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) { }
	
	private void saveDataToDatabase(File dir) {
		String language = dir.getName();
		BibleBean bible = this.findBible(language);
		if (bible == null) {
			throw new VerbumDominiException("Could not find any bible with language " + language);
		}
		
		File[] files = dir.listFiles();
		for (File file : files) {
			logger.info(" >>> " + file.getPath());
			
			BookBean book = this.buildBookBean(file);
			book.setBible(bible);
			this.dataBookBucket.saveBatch(book);
		}
	}
	
	private BookBean buildBookBean(File file) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (IOException ex) {
			throw new VerbumDominiException(ex);
		}
		
		Map<?, ?> map = JsonMapperHelper.map(Map.class, inputStream);
		BookBean book = EntityMapperHelper.map(BookBean.class, map);
		
		return book;
	}
	
	private void dropEntities() {
		List<Class<? extends IBean>> entityTypes = Arrays.asList(
			VerseBean.class, AnnotationBean.class,
			ChapterBean.class, BookBean.class
		);
		
		for (Class<? extends IBean> entityType : entityTypes) {
			DaoFactory.createDao(entityType).deleteAll();
		}
	}
	
	private BibleBean findBible(String language) {
		BibleBean filter = new BibleBeanImpl();
		filter.setLanguage(language);
		Pagination<BibleBean> res = this.bibleDao.list(filter);
		
		return (res.isEmpty()) ? null : res.getElements().get(0);
	}
	
	private File getDataDir() {
		URL url = DataSeedServletContextListener.class.getResource(DATA_PATH);
		try {
			logger.info("Data seed path: " + url);
			return new File(url.toURI());
		} catch (URISyntaxException ex) {
			throw new VerbumDominiException(ex);
		}
	}
}