package com.github.aureliano.verbum_domini.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.domain.bean.VerseBean;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class VerseDataHelper {

	private VerseDataHelper() {
		super();
	}
	
	public static void createTable() {
		SchemaHelper.createTable("hsqldb/verse-schema.sql");
	}
	
	public static void createChapters() {
		int verseId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = new ChapterBean();
			chapter.setId((i + 1));

			for (byte j = 1; j <= 5; j++) {
				VerseBean verse = prepareVerse(++verseId, String.valueOf(j), chapter);
				save(verse);
			}
		}
	}
	
	private static void save(VerseBean bean) {
		Connection connection = ConnectionSingleton.instance().getConnection();
		
		try (
			PreparedStatement ps = connection.prepareStatement("insert into verse(" +
				"id,number,text, chapter_fk) values(?,?,?,?)");
		) {
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getNumber());
			ps.setString(3, bean.getText());
			ps.setInt(4, bean.getChapter().getId());
			
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private static VerseBean prepareVerse(Integer id, String number, ChapterBean chapter) {
		VerseBean verse = new VerseBean();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}