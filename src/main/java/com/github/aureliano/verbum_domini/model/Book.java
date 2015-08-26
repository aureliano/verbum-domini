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
 * Books within the Holy Bible
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "bookId",
    "name",
    "bibleId"
})
public class Book {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("bookId")
    private Integer bookId;
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
    @JsonProperty("bibleId")
    private Integer bibleId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The bookId
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
     *     The bookId
     */
    @JsonProperty("bookId")
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Book withBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The name
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
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Book withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The bibleId
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
     *     The bibleId
     */
    @JsonProperty("bibleId")
    public void setBibleId(Integer bibleId) {
        this.bibleId = bibleId;
    }

    public Book withBibleId(Integer bibleId) {
        this.bibleId = bibleId;
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

    public Book withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}