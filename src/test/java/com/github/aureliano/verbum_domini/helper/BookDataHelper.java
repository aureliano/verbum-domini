package com.github.aureliano.verbum_domini.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public final class BookDataHelper {

	private BookDataHelper() {
		super();
	}
	
	public static void createBooks() {
		String[] names = new String[] {
			"Genesis", "Exodus", "Leviticus", "Numeri", "Deuteronomii"
		};
		
		Session session = PersistenceManager.instance().openSession();
		int[] ids = new int[] { 1, 2 };
		int bookId = 0;
		Transaction transaction = session.beginTransaction();
		
		for (int id : ids) {
			BibleBean bible = (BibleBean) session.load(BibleBean.class, id);
			
			for (String name : names) {
				BookBean book = prepareBook(++bookId, name, bible);
				session.saveOrUpdate(book);
			}
		}
		
		transaction.commit();
	}
	
	private static BookBean prepareBook(Integer id, String name, BibleBean bible) {
		BookBean book = new BookBean();
		
		book.setId(id);
		book.setName(name);
		book.setBible(bible);
		
		return book;
	}
}