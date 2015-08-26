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
 * Holy bible
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"bibleId",
	"name",
	"language",
	"url",
	"edition",
	"printedSource",
	"eletronicTranscriptionSource",
	"eletronicTranscriptionSourceUrl",
	"copyright"
})
public class Bible {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("bibleId")
	private Integer bibleId;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("name")
	private String name;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("language")
	private String language;
	/**
	 * 
	 */
	@JsonProperty("url")
	private String url;
	/**
	 * 
	 */
	@JsonProperty("edition")
	private String edition;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("printedSource")
	private String printedSource;
	/**
	 * 
	 */
	@JsonProperty("eletronicTranscriptionSource")
	private String eletronicTranscriptionSource;
	/**
	 * 
	 */
	@JsonProperty("eletronicTranscriptionSourceUrl")
	private String eletronicTranscriptionSourceUrl;
	/**
	 * 
	 */
	@JsonProperty("copyright")
	private String copyright;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *		 The bibleId
	 */
	@JsonProperty("bibleId")
	public Integer getBibleId() {
			return bibleId;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param bibleId
	 *		 The bibleId
	 */
	@JsonProperty("bibleId")
	public void setBibleId(Integer bibleId) {
			this.bibleId = bibleId;
	}

	public Bible withBibleId(Integer bibleId) {
			this.bibleId = bibleId;
			return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *		 The name
	 */
	@JsonProperty("name")
	public String getName() {
			return name;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param name
	 *		 The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
			this.name = name;
	}

	public Bible withName(String name) {
			this.name = name;
			return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *		 The language
	 */
	@JsonProperty("language")
	public String getLanguage() {
			return language;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param language
	 *		 The language
	 */
	@JsonProperty("language")
	public void setLanguage(String language) {
			this.language = language;
	}

	public Bible withLanguage(String language) {
			this.language = language;
			return this;
	}

	/**
	 * 
	 * @return
	 *		 The url
	 */
	@JsonProperty("url")
	public String getUrl() {
			return url;
	}

	/**
	 * 
	 * @param url
	 *		 The url
	 */
	@JsonProperty("url")
	public void setUrl(String url) {
			this.url = url;
	}

	public Bible withUrl(String url) {
			this.url = url;
			return this;
	}

	/**
	 * 
	 * @return
	 *		 The edition
	 */
	@JsonProperty("edition")
	public String getEdition() {
			return edition;
	}

	/**
	 * 
	 * @param edition
	 *		 The edition
	 */
	@JsonProperty("edition")
	public void setEdition(String edition) {
			this.edition = edition;
	}

	public Bible withEdition(String edition) {
			this.edition = edition;
			return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return
	 *		 The printedSource
	 */
	@JsonProperty("printedSource")
	public String getPrintedSource() {
			return printedSource;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param printedSource
	 *		 The printedSource
	 */
	@JsonProperty("printedSource")
	public void setPrintedSource(String printedSource) {
			this.printedSource = printedSource;
	}

	public Bible withPrintedSource(String printedSource) {
			this.printedSource = printedSource;
			return this;
	}

	/**
	 * 
	 * @return
	 *		 The eletronicTranscriptionSource
	 */
	@JsonProperty("eletronicTranscriptionSource")
	public String getEletronicTranscriptionSource() {
			return eletronicTranscriptionSource;
	}

	/**
	 * 
	 * @param eletronicTranscriptionSource
	 *		 The eletronicTranscriptionSource
	 */
	@JsonProperty("eletronicTranscriptionSource")
	public void setEletronicTranscriptionSource(String eletronicTranscriptionSource) {
			this.eletronicTranscriptionSource = eletronicTranscriptionSource;
	}

	public Bible withEletronicTranscriptionSource(String eletronicTranscriptionSource) {
			this.eletronicTranscriptionSource = eletronicTranscriptionSource;
			return this;
	}

	/**
	 * 
	 * @return
	 *		 The eletronicTranscriptionSourceUrl
	 */
	@JsonProperty("eletronicTranscriptionSourceUrl")
	public String getEletronicTranscriptionSourceUrl() {
			return eletronicTranscriptionSourceUrl;
	}

	/**
	 * 
	 * @param eletronicTranscriptionSourceUrl
	 *		 The eletronicTranscriptionSourceUrl
	 */
	@JsonProperty("eletronicTranscriptionSourceUrl")
	public void setEletronicTranscriptionSourceUrl(String eletronicTranscriptionSourceUrl) {
			this.eletronicTranscriptionSourceUrl = eletronicTranscriptionSourceUrl;
	}

	public Bible withEletronicTranscriptionSourceUrl(String eletronicTranscriptionSourceUrl) {
			this.eletronicTranscriptionSourceUrl = eletronicTranscriptionSourceUrl;
			return this;
	}

	/**
	 * 
	 * @return
	 *		 The copyright
	 */
	@JsonProperty("copyright")
	public String getCopyright() {
			return copyright;
	}

	/**
	 * 
	 * @param copyright
	 *		 The copyright
	 */
	@JsonProperty("copyright")
	public void setCopyright(String copyright) {
			this.copyright = copyright;
	}

	public Bible withCopyright(String copyright) {
			this.copyright = copyright;
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

	public Bible withAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
			return this;
	}
}