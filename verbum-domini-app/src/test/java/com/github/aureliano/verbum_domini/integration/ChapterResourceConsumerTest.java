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
import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.helper.AppHelper;
import com.github.aureliano.verbum_domini.helper.DataHelper;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;

public class ChapterResourceConsumerTest {

	private static final int MAX_ELEMENTS_BY_QUERY = AppConfiguration.instance().maxElementsByQuery();
	
	public ChapterResourceConsumerTest() {
		DataHelper.instance().initializeDataHelpers();
	}
	
	@Test
	public void testGetChaptersAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("chapters"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Chapters.class).createUnmarshaller();
		Chapters chapters = (Chapters) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(50), chapters.getSize());
		assertEquals(MAX_ELEMENTS_BY_QUERY, chapters.getChapters().size());
	}

	@Test
	public void testGetChapterAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("chapters/1"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Chapters.class).createUnmarshaller();
		Chapter chapter = (Chapter) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(1), chapter.getChapterId());
		assertEquals(new Integer(1), chapter.getBookId());
		assertEquals("1", chapter.getNumber());
	}
	
	@Test
	public void testGetChaptersAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("chapters"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Chapters chapters = mapper.readValue(content, Chapters.class);
		
		assertEquals(new Integer(50), chapters.getSize());
		assertEquals(MAX_ELEMENTS_BY_QUERY, chapters.getChapters().size());
	}
	
	@Test
	public void testGetChapterAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("chapters/1"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Chapter chapter = mapper.readValue(content, Chapter.class);
		
		assertEquals(new Integer(1), chapter.getChapterId());
		assertEquals(new Integer(1), chapter.getBookId());
		assertEquals("1", chapter.getNumber());
	}
}