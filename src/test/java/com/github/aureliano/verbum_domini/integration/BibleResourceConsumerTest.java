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
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Bibles;

public class BibleResourceConsumerTest {
	
	public BibleResourceConsumerTest() {
		DataHelper.instance().initializeDataHelpers();
	}
	
	@Test
	public void testGetBiblesAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("bibles"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Bibles.class).createUnmarshaller();
		Bibles bibles = (Bibles) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(4), bibles.getSize());
		assertEquals(4, bibles.getBibles().size());
	}
	
	@Test
	public void testGetBibleAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("bibles/1"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Bibles.class).createUnmarshaller();
		Bible bible = (Bible) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(1), bible.getBibleId());
		assertEquals("Libreria Editrice Vaticana", bible.getCopyright());
		assertEquals("Bibliorum Sacrorum", bible.getEdition());
		assertEquals("latin", bible.getLanguage());
		assertEquals("Nova Vulgata", bible.getName());
		assertEquals("http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html", bible.getUrl());
	}
	
	@Test
	public void testGetBiblesAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("bibles"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Bibles bibles = mapper.readValue(content, Bibles.class);
		
		assertEquals(new Integer(4), bibles.getSize());
		assertEquals(4, bibles.getBibles().size());
	}
	
	@Test
	public void testGetBibleAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("bibles/1"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Bible bible = mapper.readValue(content, Bible.class);
		
		assertEquals(new Integer(1), bible.getBibleId());
		assertEquals("Libreria Editrice Vaticana", bible.getCopyright());
		assertEquals("Bibliorum Sacrorum", bible.getEdition());
		assertEquals("latin", bible.getLanguage());
		assertEquals("Nova Vulgata", bible.getName());
		assertEquals("http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html", bible.getUrl());
	}
}