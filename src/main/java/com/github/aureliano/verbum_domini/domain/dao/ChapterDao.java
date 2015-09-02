package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.db.DatabaseFacade;
import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class ChapterDao implements IDao<ChapterBean> {

	private DatabaseFacade databaseFacade;

	public ChapterDao() {
		this.databaseFacade = DatabaseFacade.instance();
	}

	@Override
	public Pagination<ChapterBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<ChapterBean> list(ChapterBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<ChapterBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<ChapterBean> list(ChapterBean filter, ServiceParams params) {
		Pagination<ChapterBean> pagination = new Pagination<ChapterBean>();
		
		List<Map<String, Object>> data = null;
		int offset = params.getStart() - 1;
		int limit = params.getPages() * MAX_ELEMENTS_BY_QUERY;
		
		if ((filter == null) || (filter.getBook() == null)) {
			data = this.databaseFacade.find(ChapterBean.class, offset, limit);
			pagination.setSize(this.databaseFacade.count(ChapterBean.class));
		} else {
			data = this.databaseFacade.find(ChapterBean.class, "book", filter.getBook().getId(), offset, limit);
			pagination.setSize(this.databaseFacade.countFindFilter(ChapterBean.class, "book", filter.getBook().getId()));
		}
		
		pagination.setElements(this.parse(data));
		
		return pagination;
	}

	@Override
	public ChapterBean get(Serializable id) {
		ChapterBean bean = new ChapterBean();
		bean.setId((Integer) id);
		
		return this.parse(this.databaseFacade.get(bean));
	}
	
	private List<ChapterBean> parse(List<Map<String, Object>> data) {
		List<ChapterBean> list = new ArrayList<ChapterBean>(data.size());
		for (Map<String, Object> map : data) {
			list.add(this.parse(map));
		}
		
		return list;
	}
	
	private ChapterBean parse(Map<String, Object> data) {
		if (data == null) {
			return null; 
		}
		
		ChapterBean bean = new ChapterBean();
		
		bean.setId((Integer) data.get("id"));
		bean.setNumber((String) data.get("number"));
		
		BookBean book = new BookBean();
		book.setId((Integer) data.get("book_fk"));
		bean.setBook(book);
		
		return bean;
	}
}