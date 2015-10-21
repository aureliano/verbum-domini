package com.github.aureliano.verbum_domini.core.impl.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.mongodb.client.MongoCollection;

public class BibleDataHelper {

	private BibleDataHelper() {
		super();
	}
	
	public static void createBibles() {
		AppConfiguration config = AppConfiguration.instance();
		PersistenceManagerImpl persistenceManager = (PersistenceManagerImpl) config.getPersistenceManager();
		MongoCollection<BibleBeanImpl> coll = persistenceManager.fetchCollection(BibleBeanImpl.class);
		
		BibleBeanImpl latin = prepareLatin();
		coll.insertOne(latin);
		
		BibleBeanImpl english = prepareEnglish();
		coll.insertOne(english);
	}
	
	private static BibleBeanImpl prepareLatin() {
		BibleBeanImpl bean = new BibleBeanImpl();
		
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
	
	private static BibleBeanImpl prepareEnglish() {
		BibleBeanImpl bean = new BibleBeanImpl();
		
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