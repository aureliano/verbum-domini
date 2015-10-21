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
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;

public class AnnotationResourceConsumerTest {

	public AnnotationResourceConsumerTest() {
		DataHelper.instance().initializeDataHelpers();
	}
	
	@Test
	public void testGetAnnotationsAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("annotations"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Annotations.class).createUnmarshaller();
		Annotations annotations = (Annotations) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(250), annotations.getSize());
		assertEquals(25, annotations.getAnnotations().size());
	}

	@Test
	public void testGetAnnotationAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("annotations/1"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Annotations.class).createUnmarshaller();
		Annotation annotation = (Annotation) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(1), annotation.getAnnotationId());
		assertEquals(new Integer(1), annotation.getChapterId());
		assertEquals("1", annotation.getNumber());
		assertEquals("Something 1", annotation.getText());
	}
	
	@Test
	public void testGetAnnotationsAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("annotations"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Annotations annotations = mapper.readValue(content, Annotations.class);
		
		assertEquals(new Integer(250), annotations.getSize());
		assertEquals(25, annotations.getAnnotations().size());
	}
	
	@Test
	public void testGetChapterAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("annotations/1"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Annotation annotation = mapper.readValue(content, Annotation.class);
		
		assertEquals(new Integer(1), annotation.getAnnotationId());
		assertEquals(new Integer(1), annotation.getChapterId());
		assertEquals("1", annotation.getNumber());
		assertEquals("Something 1", annotation.getText());
	}
}