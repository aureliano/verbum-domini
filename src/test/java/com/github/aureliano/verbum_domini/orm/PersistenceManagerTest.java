package com.github.aureliano.verbum_domini.orm;

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
}