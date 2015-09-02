package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.db.DatabaseFacade;
import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class BookDao implements IDao<BookBean> {

	private DatabaseFacade databaseFacade;

	public BookDao() {
		this.databaseFacade = DatabaseFacade.instance();
	}

	@Override
	public Pagination<BookBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<BookBean> list(BookBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<BookBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<BookBean> list(BookBean filter, ServiceParams params) {
		Pagination<BookBean> pagination = new Pagination<BookBean>();
		
		List<Map<String, Object>> data = null;
		int offset = params.getStart() - 1;
		int limit = params.getPages() * MAX_ELEMENTS_BY_QUERY;
		
		if ((filter == null) || (filter.getBible() == null)) {
			data = this.databaseFacade.find(BookBean.class, offset, limit);
			pagination.setSize(this.databaseFacade.count(BookBean.class));
		} else {
			data = this.databaseFacade.find(BookBean.class, "bible", filter.getBible().getId(), offset, limit);
			pagination.setSize(this.databaseFacade.countFindFilter(BookBean.class, "bible", filter.getBible().getId()));
		}
		
		pagination.setElements(this.parse(data));
		
		return pagination;
	}

	@Override
	public BookBean get(Serializable id) {
		BookBean bean = new BookBean();
		bean.setId((Integer) id);
		
		return this.parse(this.databaseFacade.get(bean));
	}
	
	private List<BookBean> parse(List<Map<String, Object>> data) {
		List<BookBean> list = new ArrayList<BookBean>(data.size());
		for (Map<String, Object> map : data) {
			list.add(this.parse(map));
		}
		
		return list;
	}
	
	private BookBean parse(Map<String, Object> data) {
		if (data == null) {
			return null; 
		}
		
		BookBean bean = new BookBean();
		
		bean.setId((Integer) data.get("id"));
		bean.setName((String) data.get("name"));
		
		BibleBean bible = new BibleBean();
		bible.setId((Integer) data.get("bible_fk"));
		bean.setBible(bible);
		
		return bean;
	}
}