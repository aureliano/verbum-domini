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
	"number",
	"text"
})
@XmlRootElement
public class Annotation {

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
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

	public Annotation withNumber(String number) {
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

	public Annotation withText(String text) {
		this.text = text;
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

	public Annotation withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}