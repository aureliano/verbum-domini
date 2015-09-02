package com.github.aureliano.verbum_domini.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class BookDataHelper {

	private BookDataHelper() {
		super();
	}
	
	public static void createTable() {
		Connection connection = ConnectionSingleton.instance().getConnection();
		try (
			Statement statement = connection.createStatement();
		) {
			statement.executeUpdate(FileHelper.readFile("hsqldb/book-schema.sql"));
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	public static void createBooks() {
		String[] names = new String[] {
			"Genesis", "Exodus", "Leviticus", "Numeri", "Deuteronomii"
		};
		
		int[] ids = new int[] { 1, 2 };
		int bookId = 0;
		
		for (int id : ids) {
			BibleBean bible = new BibleBean();
			bible.setId(id);
			
			for (String name : names) {
				BookBean book = prepareBook(++bookId, name, bible);
				save(book);
			}
		}
	}
	
	private static void save(BookBean bean) {
		Connection connection = ConnectionSingleton.instance().getConnection();
		
		try (
			PreparedStatement ps = connection.prepareStatement("insert into book(" +
				"id,name,bible_fk) values(?,?,?)");
		) {
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getBible().getId());
			
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private static BookBean prepareBook(Integer id, String name, BibleBean bible) {
		BookBean book = new BookBean();
		
		book.setId(id);
		book.setName(name);
		book.setBible(bible);
		
		return book;
	}
}