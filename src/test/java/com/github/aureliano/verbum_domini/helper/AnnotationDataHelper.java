package com.github.aureliano.verbum_domini.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.domain.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class AnnotationDataHelper {

	private AnnotationDataHelper() {
		super();
	}
	
	public static void createTable() {
		SchemaHelper.createTable("hsqldb/annotation-schema.sql");
	}
	
	public static void createAnnotations() {
		int annotationId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = new ChapterBean();
			chapter.setId((i + 1));

			for (byte j = 1; j <= 5; j++) {
				AnnotationBean annotation = prepareAnnotation(++annotationId, String.valueOf(j), chapter);
				save(annotation);
			}
		}
	}
	
	private static void save(AnnotationBean bean) {
		Connection connection = ConnectionSingleton.instance().getConnection();
		
		try (
			PreparedStatement ps = connection.prepareStatement("insert into annotation(" +
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
	
	private static AnnotationBean prepareAnnotation(Integer id, String number, ChapterBean chapter) {
		AnnotationBean verse = new AnnotationBean();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}