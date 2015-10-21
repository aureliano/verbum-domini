package com.github.aureliano.verbum_domini.core.impl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.dao.UserDao;
import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.helper.DataHelper;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;

public class UserDaoImplTest {

	private static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	private UserDao dao;
	
	public UserDaoImplTest() {
		this.prepareData();
		this.dao = new UserDaoImpl();
	}
	
	@Test
	public void testLoad() {
		this.validateBean(this.dao.load(1));
	}
	
	@Test
	public void testGet() {
		this.validateBean(this.dao.get(1));
	}
	
	@Test
	public void testExist() {
		assertTrue(this.dao.exist(1));
		assertFalse(this.dao.exist(9999999));
	}
	
	@Test
	public void testList() {
		assertEquals(new Integer(2), this.dao.list().getSize());
	}
	
	@Test
	public void testListParam() {
		ServiceParams params = new ServiceParams();
		params.withPages(1).withStart(2);
		
		assertEquals(1, this.dao.list(params).getElements().size());
	}
	
	@Test
	public void testListFilter() {
		UserBean filter = new UserBeanImpl();
		assertEquals(new Integer(2), this.dao.list(filter).getSize());
		
		filter.setLogin("santo_agostinho");
		assertEquals(new Integer(1), this.dao.list(filter).getSize());
		
		filter.setLogin("santo_tomas_aquino");
		assertEquals(new Integer(0), this.dao.list(filter).getSize());
	}
	
	@Test
	public void testListParams() {
		UserBean filter = new UserBeanImpl();
		assertEquals(1, this.dao.list(filter, 1, 1).getElements().size());
		
		filter.setLogin("santo_agostinho");
		assertEquals(1, this.dao.list(filter, 1, 2).getElements().size());
		
		filter.setLogin("santo_tomas_aquino");
		assertEquals(0, this.dao.list(filter, 1, 1).getElements().size());
	}
	
	@Test
	public void testSave() {
		UserBean bean = new UserBeanImpl();
		
		bean.setActive(true);
		bean.setCreation(new Date());
		bean.setLastSignIn(new Date());
		bean.setLogin("sao_camilo_lelis");
		bean.setPassword("123456");
		bean.setSaltNumber("12345");
		
		this.dao.save(bean);
		UserBean saved = this.dao.load(3);
		this.validateEquals(bean, saved);
		
		bean.setPassword("new_pswd");
		saved.setPassword("new_pswd");
		this.dao.save(saved);
		
		saved = this.dao.load(3);
		this.validateEquals(bean, saved);
		
		this.dao.delete(saved);
	}
	
	@Test
	public void testAuthenticate() {
		assertTrue(this.dao.authenticate("santo_agostinho", "default_pswd"));
		assertFalse(this.dao.authenticate("", ""));
		assertFalse(this.dao.authenticate("santo_agostinho", "wrong"));
		assertFalse(this.dao.authenticate("wrong", "default_pswd"));
	}
	
	private void validateBean(UserBean bean) {
		assertTrue(bean.isActive());
		assertEquals("santo_agostinho", bean.getLogin());
		assertEquals("default_pswd", bean.getPassword());
		assertEquals(new Integer(1), bean.getId());
	}
	
	private void validateEquals(UserBean b1, UserBean b2) {
		assertEquals(SIMPLE_DATE_FORMATTER.format(b1.getCreation()), SIMPLE_DATE_FORMATTER.format(b2.getCreation()));
		assertEquals(b1.getId(), b2.getId());
		assertEquals(b1.getLastSignIn(), b2.getLastSignIn());
		assertEquals(b1.getLogin(), b2.getLogin());
		assertEquals(b1.getPassword(), b2.getPassword());
	}
	
	private void prepareData() {
		DataHelper.instance().initializeDataHelpers();
	}
}