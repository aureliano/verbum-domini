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
	"abbreviations"
})
@XmlRootElement
public class Abbreviations {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("size")
	private Integer size;
	
	@JsonProperty("abbreviations")
	private List<Abbreviation> abbreviations = new ArrayList<Abbreviation>();
	
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

	public Abbreviations withSize(Integer size) {
		this.size = size;
		return this;
	}

	/**
	 * 
	 * @return
	 *	 The abbreviations
	 */
	@JsonProperty("abbreviations")
	public List<Abbreviation> getAbbreviations() {
		return abbreviations;
	}

	/**
	 * 
	 * @param abbreviations
	 *	 The abbreviations
	 */
	@JsonProperty("abbreviations")
	public void setAbbreviations(List<Abbreviation> abbreviations) {
		this.abbreviations = abbreviations;
	}

	public Abbreviations withAbbreviations(List<Abbreviation> abbreviations) {
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

	public Abbreviations withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}