package com.github.aureliano.verbum_domini.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public final class BibleDataHelper {

	private BibleDataHelper() {
		super();
	}
	
	public static void createBibles() {
		Session session = PersistenceManager.instance().openSession();
		Transaction transaction = session.beginTransaction();
		
		BibleBean latin = prepareLatin();
		session.saveOrUpdate(latin);
		
		BibleBean english = prepareEnglish();
		session.saveOrUpdate(english);
		
		transaction.commit();
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