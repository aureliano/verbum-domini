package com.github.aureliano.verbum_domini.resource.bible;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Books;
import com.github.aureliano.verbum_domini.service.BooksService;

@Path("books")
public class BooksResource {

	public BooksResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBooks(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		Books books = BooksService.fetchAll(start, pages);
		return Response.status(200).entity(books).build();
	}

	@Path("{bookId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBookById(
			@PathParam("bookId") String bookId) {
		
		Book book = BooksService.fetchBookById(bookId);
		
		if (book == null) {
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(book).build();
	}
}