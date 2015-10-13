package com.github.aureliano.verbum_domini.web.bc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.data.DataBookBucket;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.impl.data.DataBookBucketImpl;
import com.github.aureliano.verbum_domini.core.validation.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.helper.EntityMapperHelper;
import com.github.aureliano.verbum_domini.helper.JsonMapperHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.DataPage;

public final class BookBC {

	private BookBC() {
		super();
	}
	
	public static DataPage createDataPage(BookBean filter, Integer pageIndex) {
		if (pageIndex == null) {
			pageIndex = 1;
		}
		
		final int pageSize = 10;
		final int firstElement = (pageIndex * pageSize - (pageSize - 1));
		
		Pagination<BookBean> pagination = DaoFactory.createDao(BookBean.class)
				.list(filter, firstElement, pageSize);
		
		return new DataPage()
			.withData(pagination.getElements())
			.withTotal(pagination.getSize())
			.withPageSize(pageSize)
			.withPageIndex(pageIndex);
	}
	
	public static BookBean fetchBook(Integer id) {
		BookBean book = DaoFactory.createDao(BookBean.class).load(id);
		if (book.getBible() != null && StringUtils.isEmpty(book.getBible().getName())) {
			BibleBean bible = BibleBC.fetchBible(book.getBible().getId());
			book.setBible(bible);
		}
		
		return book;
	}

	public static void save(BookBean book) {
		IDao<BookBean> dao = DaoFactory.createDao(BookBean.class);
		BookBean entity = ((book.getId() != null) ? dao.load(book.getId()) : new BookBeanImpl());
		
		entity.setName(book.getName());
		
		List<String> messages = ValidationHelper.validate(entity, Save.class);
		if (messages.isEmpty()) {
			dao.save(entity);
			return;
		}
		
		List<FacesMessage> facesMessages = new ArrayList<>();
		for (String message : messages) {
			facesMessages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		}
		
		WebHelper.addMessagesToContext(facesMessages);
		throw new ValidationException();
	}
	
	public static Integer importBook(BibleBean bible, InputStream stream) {
		Map<?, ?> data = JsonMapperHelper.map(Map.class, stream);
		BookBean book = EntityMapperHelper.map(BookBean.class, data);
		book.setBible(bible);
		
		DataBookBucket dataBucket = new DataBookBucketImpl();
		return (Integer) dataBucket.saveBatch(book);
	}
}