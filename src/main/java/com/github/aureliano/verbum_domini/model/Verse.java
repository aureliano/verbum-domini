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
 * Verse of a chapter.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"verseId",
	"number",
	"text",
	"chapterId"
})
public class Verse {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("verseId")
	private Integer verseId;
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
	@JsonProperty("text")
	private String text;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("chapterId")
	private Integer chapterId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The verseId
	 */
	@JsonProperty("verseId")
	public Integer getVerseId() {
		return verseId;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param verseId
	 *	 The verseId
	 */
	@JsonProperty("verseId")
	public void setVerseId(Integer verseId) {
		this.verseId = verseId;
	}

	public Verse withVerseId(Integer verseId) {
		this.verseId = verseId;
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

	public Verse withNumber(String number) {
		this.number = number;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The text
	 */
	@JsonProperty("text")
	public String getText() {
		return text;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param text
	 *	 The text
	 */
	@JsonProperty("text")
	public void setText(String text) {
		this.text = text;
	}

	public Verse withText(String text) {
		this.text = text;
		return this;
	}

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

	public Verse withChapterId(Integer chapterId) {
		this.chapterId = chapterId;
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

	public Verse withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}