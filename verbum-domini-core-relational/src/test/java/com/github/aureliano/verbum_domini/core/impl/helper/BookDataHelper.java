package com.github.aureliano.verbum_domini.core.impl.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;

public final class BookDataHelper {

	private BookDataHelper() {
		super();
	}
	
	public static void createBooks() {
		String[] names = new String[] {
			"Genesis", "Exodus", "Leviticus", "Numeri", "Deuteronomii"
		};
		
		Session session = ((PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager()).openSession();
		int[] ids = new int[] { 1, 2 };
		int bookId = 0;
		Transaction transaction = session.beginTransaction();
		
		for (int id : ids) {
			BibleBean bible = (BibleBean) session.load(BibleBeanImpl.class, id);
			
			for (String name : names) {
				BookBean book = prepareBook(++bookId, name, bible);
				session.saveOrUpdate(book);
			}
		}
		
		transaction.commit();
	}
	
	private static BookBean prepareBook(Integer id, String name, BibleBean bible) {
		BookBean book = new BookBeanImpl();
		
		book.setId(id);
		book.setName(name);
		book.setBible(bible);
		
		return book;
	}
}