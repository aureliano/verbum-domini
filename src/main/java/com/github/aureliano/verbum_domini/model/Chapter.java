package com.github.aureliano.verbum_domini.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Chapter of a book.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"chapterId",
	"number",
	"bookId"
})
public class Chapter {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("chapterId")
	private Integer chapterId;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("number")
	private String number;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("bookId")
	private Integer bookId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The chapterId
	 */
	@JsonProperty("chapterId")
	public Integer getChapterId() {
		return chapterId;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param chapterId
	 *	 The chapterId
	 */
	@JsonProperty("chapterId")
	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public Chapter withChapterId(Integer chapterId) {
		this.chapterId = chapterId;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The number
	 */
	@JsonProperty("number")
	public String getNumber() {
		return number;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param number
	 *	 The number
	 */
	@JsonProperty("number")
	public void setNumber(String number) {
		this.number = number;
	}

	public Chapter withNumber(String number) {
		this.number = number;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The bookId
	 */
	@JsonProperty("bookId")
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param bookId
	 *	 The bookId
	 */
	@JsonProperty("bookId")
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Chapter withBookId(Integer bookId) {
		this.bookId = bookId;
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

	public Chapter withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}