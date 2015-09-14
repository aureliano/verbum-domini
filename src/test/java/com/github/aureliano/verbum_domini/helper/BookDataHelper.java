package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class BookDataHelper {

	private BookDataHelper() {
		super();
	}
	
	public static void createBooks() {
		String[] names = new String[] {
			"Genesis", "Exodus", "Leviticus", "Numeri", "Deuteronomii"
		};
		
		IDao<BookBean> dao = DaoFactory.createDao(BookBean.class);
		IDao<BibleBean> bibleDao = DaoFactory.createDao(BibleBean.class);
		
		int[] ids = new int[] { 1, 2 };
		int bookId = 0;
		
		for (int id : ids) {
			BibleBean bible = (BibleBean) bibleDao.load(id);
			
			for (String name : names) {
				BookBean book = prepareBook(++bookId, name, bible);
				dao.save(book);
			}
		}
	}
	
	private static BookBean prepareBook(Integer id, String name, BibleBean bible) {
		BookBean book = new BookBeanImpl();
		
		book.setId(id);
		book.setName(name);
		book.setBible(bible);
		
		return book;
	}
}