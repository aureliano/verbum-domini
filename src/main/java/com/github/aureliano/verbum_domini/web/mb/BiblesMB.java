package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.github.aureliano.verbum_domini.web.DataPage;
import com.github.aureliano.verbum_domini.web.bc.BiblesBC;

@ManagedBean(name = "biblesMB")
@RequestScoped
public class BiblesMB {

	public BiblesMB() {
		super();
	}
	
	public DataPage getDataPage() {
		return BiblesBC.createDataPage(null, null);
	}
}