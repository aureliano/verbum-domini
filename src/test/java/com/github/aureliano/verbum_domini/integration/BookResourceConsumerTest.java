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
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Books;

public class BookResourceConsumerTest {

	@Test
	public void testGetBooksAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("books"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Books.class).createUnmarshaller();
		Books books = (Books) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(295), books.getSize());
		assertEquals(25, books.getBooks().size());
	}

	@Test
	public void testGetBookAsXml() throws JAXBException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("books/1"));
		
		String content = target.request(MediaType.APPLICATION_XML).get(String.class);
		client.close();
		
		Unmarshaller u = JAXBContext.newInstance(Books.class).createUnmarshaller();
		Book book = (Book) u.unmarshal(new StringReader(content));
		
		assertEquals(new Integer(1), book.getBookId());
		assertEquals(new Integer(1), book.getBibleId());
		assertEquals("numeri", book.getName());
	}
	
	@Test
	public void testGetBooksAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("books"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Books books = mapper.readValue(content, Books.class);
		
		assertEquals(new Integer(295), books.getSize());
		assertEquals(25, books.getBooks().size());
	}
	
	@Test
	public void testGetBookAsJson() throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(AppHelper.getServiceUrl("books/1"));
		
		String content = target.request(MediaType.APPLICATION_JSON).get(String.class);
		client.close();
		
		ObjectMapper mapper = new ObjectMapper();
		Book book = mapper.readValue(content, Book.class);
		
		assertEquals(new Integer(1), book.getBookId());
		assertEquals(new Integer(1), book.getBibleId());
		assertEquals("numeri", book.getName());
	}
}