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
 * Available verses of a chapter.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"size",
	"verses"
})
@XmlRootElement
public class Verses {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("size")
	private Integer size;
	
	@JsonProperty("verses")
	private List<Verse> verses = new ArrayList<Verse>();
	
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

	public Verses withSize(Integer size) {
		this.size = size;
		return this;
	}

	/**
	 * 
	 * @return
	 *	 The verses
	 */
	@JsonProperty("verses")
	public List<Verse> getVerses() {
		return verses;
	}

	/**
	 * 
	 * @param verses
	 *	 The verses
	 */
	@JsonProperty("verses")
	public void setVerses(List<Verse> verses) {
		this.verses = verses;
	}

	public Verses withVerses(List<Verse> verses) {
		this.verses = verses;
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

	public Verses withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}