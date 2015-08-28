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
 * Available annotations of a chapter.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"size",
	"annotations"
})
public class Annotations {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("size")
	private Integer size;
	@JsonProperty("annotations")
	private List<Annotation> annotations = new ArrayList<Annotation>();
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

	public Annotations withSize(Integer size) {
		this.size = size;
		return this;
	}

	/**
	 * 
	 * @return
	 *	 The annotations
	 */
	@JsonProperty("annotations")
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * 
	 * @param annotations
	 *	 The annotations
	 */
	@JsonProperty("annotations")
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Annotations withAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
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

	public Annotations withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}