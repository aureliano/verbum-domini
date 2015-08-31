package com.github.aureliano.verbum_domini.orm;

import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class PersistenceManagerTest {

	@Test
	public void testInstance() {
		PersistenceManager p1 = PersistenceManager.instance();
		Assert.assertNotNull(p1);
		
		PersistenceManager p2 = PersistenceManager.instance();
		Assert.assertEquals(p1, p2);
		Assert.assertTrue(p1 == p2);
	}
	
	@Test
	public void testCreateDefaultConfiguration() {
		PersistenceManager pm = PersistenceManager.instance();
		String actual = pm.createDefaultConfiguration("sql").getClass().getSimpleName();
		Assert.assertEquals(Configuration.class.getSimpleName(), actual);
		
		actual = pm.createDefaultConfiguration("SQL").getClass().getSimpleName();
		Assert.assertEquals(Configuration.class.getSimpleName(), actual);
		
		/*actual = pm.createDefaultConfiguration("nosql").getClass().getSimpleName();
		Assert.assertEquals(OgmConfiguration.class.getSimpleName(), actual);
		
		actual = pm.createDefaultConfiguration("NoSQL").getClass().getSimpleName();
		Assert.assertEquals(OgmConfiguration.class.getSimpleName(), actual);*/
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testCreateDefaultConfigurationUnsupportedException() {
		PersistenceManager.instance().createDefaultConfiguration("malabibala");
	}
}