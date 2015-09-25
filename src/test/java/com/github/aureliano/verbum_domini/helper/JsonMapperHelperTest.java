package com.github.aureliano.verbum_domini.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

public class JsonMapperHelperTest {

	@Test
	public void testMap() throws FileNotFoundException {
		File file = new File("src/test/resources/Luke.json");
		InputStream stream = new FileInputStream(file);
		
		Map<String, Object> map = JsonMapperHelper.map(Map.class, stream);
		assertEquals("Luke", map.get("book"));
		
		map = (Map<String, Object>) map.get("chapters");
		assertNotNull(map);
		
		for (String key : map.keySet()) {
			Map<?, ?> data = (Map<?, ?>) map.get(key);
			
			assertTrue(map.get(key) instanceof Map);
			assertTrue(data.get("verses") instanceof Map);
			assertTrue(data.get("annotations") instanceof Map);
		}
	}
	
	@Test
	public void testGetMap() throws FileNotFoundException {
		File file = new File("src/test/resources/Luke.json");
		InputStream stream = new FileInputStream(file);
		
		Map<String, Object> map = JsonMapperHelper.map(Map.class, stream);
		assertEquals("Luke", map.get("book"));
		
		map = JsonMapperHelper.getMap(Map.class, map, "chapters");
		assertNotNull(map);
	}
}