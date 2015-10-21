package com.github.aureliano.verbum_domini.core.impl.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;
import com.mongodb.client.MongoCollection;

public final class BookDataHelper {

	private BookDataHelper() {
		super();
	}

	public static void createBooks() {
		AppConfiguration config = AppConfiguration.instance();
		PersistenceManagerImpl persistenceManager = (PersistenceManagerImpl) config.getPersistenceManager();
		MongoCollection<BookBeanImpl> beanWriter = persistenceManager.fetchCollection(BookBeanImpl.class);
		
		String[] names = new String[] {
			"Genesis", "Exodus", "Leviticus", "Numeri", "Deuteronomii"
		};
		
		Integer[] ids = new Integer[] { 1, 2 };
		int bookId = 0;
		
		for (Integer id : ids) {
			BibleBean bible = DaoHelper.findOne(BibleBeanImpl.class, id);
			
			for (String name : names) {
				BookBeanImpl book = prepareBook(++bookId, name, bible);
				beanWriter.insertOne(book);
			}
		}
	}
	
	private static BookBeanImpl prepareBook(Integer id, String name, BibleBean bible) {
		BookBeanImpl book = new BookBeanImpl();
		
		book.setId(id);
		book.setName(name);
		book.setBible(bible);
		
		return book;
	}
}