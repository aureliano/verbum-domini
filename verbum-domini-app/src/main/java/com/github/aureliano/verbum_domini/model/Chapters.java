package com.github.aureliano.verbum_domini.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Available chapters of a book.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"size",
	"chapters"
})
@XmlRootElement
public class Chapters {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("size")
	private Integer size;
	
	@JsonProperty("chapters")
	private List<Chapter> chapters = new ArrayList<Chapter>();
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *	 The size
	 */
	@JsonProperty("size")
	public Integer getSize() {
		return size;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param size
	 *	 The size
	 */
	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}

	public Chapters withSize(Integer size) {
		this.size = size;
		return this;
	}

	/**
	 * 
	 * @return
	 *	 The chapters
	 */
	@JsonProperty("chapters")
	public List<Chapter> getChapters() {
		return chapters;
	}

	/**
	 * 
	 * @param chapters
	 *	 The chapters
	 */
	@JsonProperty("chapters")
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Chapters withChapters(List<Chapter> chapters) {
		this.chapters = chapters;
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

	public Chapters withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}