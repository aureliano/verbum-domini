package com.github.aureliano.verbum_domini.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class ChapterDataHelper {

	private ChapterDataHelper() {
		super();
	}
	
	public static void createTable() {
		Connection connection = ConnectionSingleton.instance().getConnection();
		try (
			Statement statement = connection.createStatement();
		) {
			statement.executeUpdate(FileHelper.readFile("hsqldb/chapter-schema.sql"));
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	public static void createChapters() {
		int chapterId = 0;
		
		for (int i = 0; i < 10; i++) {
			BookBean book = new BookBean();
			book.setId(i + 1);

			for (byte j = 1; j <= 5; j++) {
				ChapterBean chapter = prepareChapter(++chapterId, String.valueOf(j), book);
				save(chapter);
			}
		}
	}
	
	private static void save(ChapterBean bean) {
		Connection connection = ConnectionSingleton.instance().getConnection();
		
		try (
			PreparedStatement ps = connection.prepareStatement("insert into chapter(" +
				"id,number,book_fk) values(?,?,?)");
		) {
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getNumber());
			ps.setInt(3, bean.getBook().getId());
			
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private static ChapterBean prepareChapter(Integer id, String number, BookBean book) {
		ChapterBean chapter = new ChapterBean();
		
		chapter.setId(id);
		chapter.setNumber(number);
		chapter.setBook(book);
		
		return chapter;
	}
}