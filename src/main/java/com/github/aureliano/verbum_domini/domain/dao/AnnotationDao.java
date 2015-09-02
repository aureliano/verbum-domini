package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.db.DatabaseFacade;
import com.github.aureliano.verbum_domini.domain.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class AnnotationDao implements IDao<AnnotationBean> {

	private DatabaseFacade databaseFacade;
	
	public AnnotationDao() {
		this.databaseFacade = DatabaseFacade.instance();
	}

	@Override
	public Pagination<AnnotationBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}

	@Override
	public Pagination<AnnotationBean> list(ServiceParams params) {
		return this.list(null, params);
	}

	@Override
	public Pagination<AnnotationBean> list(AnnotationBean filter, ServiceParams params) {
		Pagination<AnnotationBean> pagination = new Pagination<AnnotationBean>();
		
		List<Map<String, Object>> data = null;
		int offset = params.getStart() - 1;
		int limit = params.getPages() * MAX_ELEMENTS_BY_QUERY;
		
		if ((filter == null) || (filter.getChapter() == null)) {
			data = this.databaseFacade.find(AnnotationBean.class, offset, limit);
			pagination.setSize(this.databaseFacade.count(AnnotationBean.class));
		} else {
			data = this.databaseFacade.find(AnnotationBean.class, "chapter", filter.getChapter().getId(), offset, limit);
			pagination.setSize(this.databaseFacade.countFindFilter(AnnotationBean.class, "chapter", filter.getChapter().getId()));
		}
		
		pagination.setElements(this.parse(data));
		
		return pagination;
	}

	@Override
	public AnnotationBean get(Serializable id) {
		AnnotationBean bean = new AnnotationBean();
		bean.setId((Integer) id);
		
		return this.parse(this.databaseFacade.get(bean));
	}
	
	private List<AnnotationBean> parse(List<Map<String, Object>> data) {
		List<AnnotationBean> list = new ArrayList<AnnotationBean>(data.size());
		for (Map<String, Object> map : data) {
			list.add(this.parse(map));
		}
		
		return list;
	}
	
	private AnnotationBean parse(Map<String, Object> data) {
		if (data == null) {
			return null; 
		}
		
		AnnotationBean bean = new AnnotationBean();
		
		bean.setId((Integer) data.get("id"));
		bean.setNumber((String) data.get("number"));
		bean.setText((String) data.get("text"));
		ChapterBean chapter = new ChapterBean();
		chapter.setId((Integer) data.get("chapter_fk"));
		bean.setChapter(chapter);
		
		return bean;
	}
}