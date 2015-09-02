package com.github.aureliano.verbum_domini.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class BibleDataHelper {

	private BibleDataHelper() {
		super();
	}
	
	public static void createTable() {
		Connection connection = ConnectionSingleton.instance().getConnection();
		try (
			Statement statement = connection.createStatement();
		) {
			statement.executeUpdate(FileHelper.readFile("hsqldb/bible-schema.sql"));
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	public static void createBibles() {
		BibleBean latin = prepareLatin();
		save(latin);
		
		BibleBean english = prepareEnglish();
		save(english);
	}
	
	private static void save(BibleBean bean) {
		Connection connection = ConnectionSingleton.instance().getConnection();
		
		try (
			PreparedStatement ps = connection.prepareStatement("insert into bible(" +
				"copyright,edition, eletronic_transcription_source, eletronic_transcription_source_url," +
				"id,language,name,printed_source,url) values(?,?,?,?,?,?,?,?,?)");
		) {
			ps.setString(1, bean.getCopyright());
			ps.setString(2, bean.getEdition());
			ps.setString(3, bean.getEletronicTranscriptionSource());
			ps.setString(4, bean.getEletronicTranscriptionSourceUrl());
			ps.setInt(5, bean.getId());
			ps.setString(6, bean.getLanguage());
			ps.setString(7, bean.getName());
			ps.setString(8, bean.getPrintedSource());
			ps.setString(9, bean.getUrl());
			
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private static BibleBean prepareLatin() {
		BibleBean bean = new BibleBean();
		
		bean.setCopyright("Libreria Editrice Vaticana");
		bean.setEdition("Bibliorum Sacrorum");
		bean.setEletronicTranscriptionSource(null);
		bean.setEletronicTranscriptionSourceUrl(null);
		bean.setId(1);
		bean.setLanguage("latin");
		bean.setName("Nova Vulgata");
		bean.setPrintedSource(null);
		bean.setUrl("http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html");
		
		return bean;
	}
	
	private static BibleBean prepareEnglish() {
		BibleBean bean = new BibleBean();
		
		bean.setCopyright("Libreria Editrice Vaticana");
		bean.setEdition(null);
		bean.setEletronicTranscriptionSource("United States Conference of Catholic Bishops");
		bean.setEletronicTranscriptionSourceUrl("http://www.nccbuscc.org/");
		bean.setId(2);
		bean.setLanguage("english");
		bean.setName("THE NEW AMERICAN BIBLE");
		bean.setPrintedSource("United States Conference of Catholic Bishops\n3211 4th Street, N.E., Washington, DC 20017-1194 (202) 541-3000\nNovember 11, 2002 Copyright (c) by United States Conference of Catholic Bishops");
		bean.setUrl("http://www.vatican.va/archive/ENG0839/_INDEX.HTM");
		
		return bean;
	}
}