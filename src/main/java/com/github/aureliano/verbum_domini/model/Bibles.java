package com.github.aureliano.verbum_domini.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Available Holy Bible collection
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"size",
	"bibles"
})
public class Bibles {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("size")
	private Integer size;
	@JsonProperty("bibles")
	private List<Bible> bibles = new ArrayList<Bible>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *		 The size
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
	 *		 The size
	 */
	@JsonProperty("size")
	public void setSize(Integer size) {
			this.size = size;
	}

	public Bibles withSize(Integer size) {
			this.size = size;
			return this;
	}

	/**
	 * 
	 * @return
	 *		 The bibles
	 */
	@JsonProperty("bibles")
	public List<Bible> getBibles() {
			return bibles;
	}

	/**
	 * 
	 * @param bibles
	 *		 The bibles
	 */
	@JsonProperty("bibles")
	public void setBibles(List<Bible> bibles) {
			this.bibles = bibles;
	}

	public Bibles withBibles(List<Bible> bibles) {
			this.bibles = bibles;
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

	public Bibles withAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
			return this;
	}
}