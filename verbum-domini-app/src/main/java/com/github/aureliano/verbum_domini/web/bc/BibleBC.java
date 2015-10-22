package com.github.aureliano.verbum_domini.web.bc;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.validation.ValidationException;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.helper.ValidationHelper;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.validation.Save;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.DataPage;

public final class BibleBC {

	private BibleBC() {
		super();
	}
	
	public static DataPage createDataPage(BibleBean filter, Integer pageIndex) {
		if (pageIndex == null) {
			pageIndex = 1;
		}
		
		final int pageSize = 10;
		final int firstElement = (pageIndex * pageSize - (pageSize - 1));
		
		Pagination<BibleBean> pagination = DaoFactory.createDao(BibleBean.class)
				.list(filter, firstElement, pageSize);
		
		return new DataPage()
			.withData(pagination.getElements())
			.withTotal(pagination.getSize())
			.withPageSize(pageSize)
			.withPageIndex(pageIndex);
	}
	
	public static BibleBean fetchBible(Integer id) {
		return DaoFactory.createDao(BibleBean.class).load(id);
	}
	
	public static void save(BibleBean bible) {
		IDao<BibleBean> dao = DaoFactory.createDao(BibleBean.class);
		BibleBean entity = ((bible.getId() != null) ? dao.get(bible.getId()) : new BibleBeanImpl());
		
		if (entity == null) {
			entity = new BibleBeanImpl();
		}
		
		entity.setName(bible.getName());
		entity.setLanguage(bible.getLanguage());
		entity.setUrl(bible.getUrl());
		entity.setEdition(bible.getEdition());
		entity.setPrintedSource(bible.getPrintedSource());
		entity.setEletronicTranscriptionSource(bible.getEletronicTranscriptionSource());
		entity.setEletronicTranscriptionSourceUrl(bible.getEletronicTranscriptionSourceUrl());
		entity.setCopyright(bible.getCopyright());
		
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
	
	public static void defineEmptyValuesAsNull(BibleBean bible) {
		if ("".equals(bible.getCopyright())) {
			bible.setCopyright(null);
		}
		if ("".equals(bible.getEdition())) {
			bible.setEdition(null);
		}
		if ("".equals(bible.getEletronicTranscriptionSource())) {
			bible.setEletronicTranscriptionSource(null);
		}
		if ("".equals(bible.getEletronicTranscriptionSourceUrl())) {
			bible.setEletronicTranscriptionSourceUrl(null);
		}
		if ("".equals(bible.getLanguage())) {
			bible.setLanguage(null);
		}
		if ("".equals(bible.getName())) {
			bible.setName(null);
		}
		if ("".equals(bible.getPrintedSource())) {
			bible.setPrintedSource(null);
		}
		if ("".equals(bible.getUrl())) {
			bible.setUrl(null);
		}
	}
}