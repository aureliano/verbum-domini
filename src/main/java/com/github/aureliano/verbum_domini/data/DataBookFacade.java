package com.github.aureliano.verbum_domini.data;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.data.DataBookBucket;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.impl.data.DataBookBucketImpl;
import com.github.aureliano.verbum_domini.helper.EntityMapperHelper;
import com.github.aureliano.verbum_domini.helper.JsonMapperHelper;

public final class DataBookFacade {

	private DataBookFacade() {
		super();
	}
	
	public static BookBean save(Serializable bibleId, InputStream inputStream) {
		Map<?, ?> map = JsonMapperHelper.map(Map.class, inputStream);
		BookBean book = EntityMapperHelper.map(BookBean.class, map);
		
		BibleBean bible = DaoFactory.createDao(BibleBean.class).load(bibleId);
		book.setBible(bible);
		
		DataBookBucket dataBucket = new DataBookBucketImpl();
		Serializable id = dataBucket.saveBatch(book);
		
		return DaoFactory.createDao(BookBean.class).load(id);
	}
}