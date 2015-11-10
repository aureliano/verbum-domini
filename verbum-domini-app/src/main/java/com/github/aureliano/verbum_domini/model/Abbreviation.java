package com.github.aureliano.verbum_domini.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Annotation of a chapter.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"book",
	"abbreviations"
})
@XmlRootElement
public class Abbreviation {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("book")
	private String book;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("abbreviations")
	private String[] abbreviations;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The book
	 */
	@JsonProperty("book")
	public String getBook() {
		return book;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param book
	 *	 The book
	 */
	@JsonProperty("book")
	public void setBook(String book) {
		this.book = book;
	}

	public Abbreviation withBook(String book) {
		this.book = book;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The abbreviations
	 */
	@JsonProperty("abbreviations")
	public String[] getAbbreviations() {
		return abbreviations;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param abbreviations
	 *	 The abbreviations
	 */
	@JsonProperty("abbreviations")
	public void setAbbreviations(String[] abbreviations) {
		this.abbreviations = abbreviations;
	}

	public Abbreviation withAbbreviations(String[] abbreviations) {
		this.abbreviations = abbreviations;
		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public Abbreviation withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}