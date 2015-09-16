package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class BibleDataHelper {

	private BibleDataHelper() {
		super();
	}
	
	public static void createBibles() {
		IDao<BibleBean> dao = DaoFactory.createDao(BibleBean.class);
		
		BibleBean latin = prepareLatin();
		dao.save(latin);
		
		BibleBean english = prepareEnglish();
		dao.save(english);
	}
	
	private static BibleBean prepareLatin() {
		BibleBean bean = new BibleBeanImpl();
		
		bean.setCopyright("Libreria Editrice Vaticana");
		bean.setEdition("Bibliorum Sacrorum");
		bean.setEletronicTranscriptionSource(null);
		bean.setEletronicTranscriptionSourceUrl(null);
		bean.setLanguage("latin");
		bean.setName("Nova Vulgata");
		bean.setPrintedSource(null);
		bean.setUrl("http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html");
		
		return bean;
	}
	
	private static BibleBean prepareEnglish() {
		BibleBean bean = new BibleBeanImpl();
		
		bean.setCopyright("Libreria Editrice Vaticana");
		bean.setEdition(null);
		bean.setEletronicTranscriptionSource("United States Conference of Catholic Bishops");
		bean.setEletronicTranscriptionSourceUrl("http://www.nccbuscc.org/");
		bean.setLanguage("english");
		bean.setName("THE NEW AMERICAN BIBLE");
		bean.setPrintedSource("United States Conference of Catholic Bishops\n3211 4th Street, N.E., Washington, DC 20017-1194 (202) 541-3000\nNovember 11, 2002 Copyright (c) by United States Conference of Catholic Bishops");
		bean.setUrl("http://www.vatican.va/archive/ENG0839/_INDEX.HTM");
		
		return bean;
	}
}