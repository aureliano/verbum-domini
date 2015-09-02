package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.db.DatabaseFacade;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.domain.bean.VerseBean;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class VerseDao implements IDao<VerseBean> {

	private DatabaseFacade databaseFacade;
	
	public VerseDao() {
		this.databaseFacade = DatabaseFacade.instance();
	}

	@Override
	public Pagination<VerseBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<VerseBean> list(VerseBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<VerseBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<VerseBean> list(VerseBean filter, ServiceParams params) {
		Pagination<VerseBean> pagination = new Pagination<VerseBean>();
		
		List<Map<String, Object>> data = null;
		int offset = params.getStart() - 1;
		int limit = params.getPages() * MAX_ELEMENTS_BY_QUERY;
		
		if ((filter == null) || (filter.getChapter() == null)) {
			data = this.databaseFacade.find(VerseBean.class, offset, limit);
			pagination.setSize(this.databaseFacade.count(VerseBean.class));
		} else {
			data = this.databaseFacade.find(VerseBean.class, "chapter", filter.getChapter().getId(), offset, limit);
			pagination.setSize(this.databaseFacade.countFindFilter(VerseBean.class, "chapter", filter.getChapter().getId()));
		}
		
		pagination.setElements(this.parse(data));
		
		return pagination;
	}

	@Override
	public VerseBean get(Serializable id) {
		VerseBean bean = new VerseBean();
		bean.setId((Integer) id);
		
		return this.parse(this.databaseFacade.get(bean));
	}
	
	private List<VerseBean> parse(List<Map<String, Object>> data) {
		List<VerseBean> list = new ArrayList<VerseBean>(data.size());
		for (Map<String, Object> map : data) {
			list.add(this.parse(map));
		}
		
		return list;
	}
	
	private VerseBean parse(Map<String, Object> data) {
		if (data == null) {
			return null; 
		}
		
		VerseBean bean = new VerseBean();
		
		bean.setId((Integer) data.get("id"));
		bean.setNumber((String) data.get("number"));
		bean.setText((String) data.get("text"));
		System.out.println(data);
		ChapterBean chapter = new ChapterBean();
		chapter.setId((Integer) data.get("chapter_fk"));
		bean.setChapter(chapter);
		
		return bean;
	}
}