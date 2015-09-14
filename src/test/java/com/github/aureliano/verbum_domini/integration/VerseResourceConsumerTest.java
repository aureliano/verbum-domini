package com.github.aureliano.verbum_domini.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.verbum_domini.helper.AppHelper;
import com.github.aureliano.verbum_domini.helper.DataHelper;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;

public class VerseResourceConsumerTest {

	public VerseResourceConsumerTest() {
		DataHelper.instance().initializeDataHelpers();
	}
	
	@Test
	public void testGetVersesAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("verses"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Verses.class).createUnmarshaller();
		Verses verses = (Verses) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(140005), verses.getSize());
		assertEquals(25, verses.getVerses().size());
	}

	@Test
	public void testGetVerseAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("verses/1"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Verses.class).createUnmarshaller();
		Verse verse = (Verse) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(1), verse.getVerseId());
		assertEquals(new Integer(1), verse.getChapterId());
		assertEquals("1L", verse.getNumber());
	}
	
	@Test
	public void testGetVersesAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("verses"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Verses verses = mapper.readValue(content, Verses.class);
		
		assertEquals(new Integer(140005), verses.getSize());
		assertEquals(25, verses.getVerses().size());
	}
	
	@Test
	public void testGetChapterAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("verses/1"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Verse verse = mapper.readValue(content, Verse.class);
		
		assertEquals(new Integer(1), verse.getVerseId());
		assertEquals(new Integer(1), verse.getChapterId());
		assertEquals("1L", verse.getNumber());
	}
}