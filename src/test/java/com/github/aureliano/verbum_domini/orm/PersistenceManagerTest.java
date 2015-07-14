package com.github.aureliano.verbum_domini.orm;

import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.verbum_domini.bean.BibleBean;

public class PersistenceManagerTest {

	@Test
	public void testInstance() {
		Assert.assertNotNull(PersistenceManager.instance());
	}
	
	@Test
	public void testGetUserName() {
		Assert.assertEquals("postgres", PersistenceManager.instance().getUserName());
	}
	
	@Test
	public void testGetUserPassword() {
		Assert.assertEquals("postgres", PersistenceManager.instance().getUserPassword());
	}
	
	@Test
	public void testGetDatabaseUrl() {
		Assert.assertEquals("jdbc:postgresql://localhost:5432/verbum_domini", PersistenceManager.instance().getDatabaseUrl());
	}
	
	@Test
	public void testBuildConfiguration() {
		Configuration configuration = PersistenceManager.instance().buildConfiguration();
		Properties prop = configuration.getProperties();
		
		Assert.assertNotNull(configuration.getClassMapping(BibleBean.class.getName()));
	}
}