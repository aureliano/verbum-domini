package com.github.aureliano.verbum_domini.domain.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.verbum_domini.db.DatabaseFacade;
import com.github.aureliano.verbum_domini.domain.bean.BibleBean;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public class BibleDao implements IDao<BibleBean> {

	private DatabaseFacade databaseFacade;
	
	public BibleDao() {
		this.databaseFacade = DatabaseFacade.instance();
	}
	
	@Override
	public BibleBean get(Serializable id) {
		BibleBean bean = new BibleBean();
		bean.setId((Integer) id);
		
		return this.parse(this.databaseFacade.get(bean));
	}
	
	@Override
	public Pagination<BibleBean> list() {
		return this.list(null, ServiceParams.createDefault());
	}
	
	@Override
	public Pagination<BibleBean> list(BibleBean filter) {
		return this.list(filter, ServiceParams.createDefault());
	}
	
	@Override
	public Pagination<BibleBean> list(ServiceParams params) {
		return this.list(null, params);
	}
	
	@Override
	public Pagination<BibleBean> list(BibleBean filter, ServiceParams params) {
		Pagination<BibleBean> pagination = new Pagination<BibleBean>();
		
		pagination.setSize(this.databaseFacade.count(BibleBean.class));
		
		List<Map<String, Object>> data = null;
		int offset = params.getStart() - 1;
		int limit = params.getPages() * MAX_ELEMENTS_BY_QUERY;
		
		data = this.databaseFacade.find(BibleBean.class, offset, limit);
		
		pagination.setElements(this.parse(data));
		
		return pagination;
	}
	
	private List<BibleBean> parse(List<Map<String, Object>> data) {
		List<BibleBean> list = new ArrayList<BibleBean>(data.size());
		for (Map<String, Object> map : data) {
			list.add(this.parse(map));
		}
		
		return list;
	}
	
	private BibleBean parse(Map<String, Object> data) {
		if (data == null) {
			return null; 
		}
		
		BibleBean bean = new BibleBean();
		
		bean.setId((Integer) data.get("id"));
		bean.setCopyright((String) data.get("copyright"));
		bean.setEdition((String) data.get("edition"));
		bean.setEletronicTranscriptionSource((String) data.get("eletronic_transcription_source"));
		bean.setEletronicTranscriptionSourceUrl((String) data.get("eletronic_transcription_source_url"));
		bean.setLanguage((String) data.get("language"));
		bean.setName((String) data.get("name"));
		bean.setPrintedSource((String) data.get("printed_source"));
		bean.setUrl((String) data.get("url"));
		
		return bean;
	}
}