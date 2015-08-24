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
 * Available books within the Holy Bible
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "size",
    "books"
})
public class Books {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("books")
    private List<Book> books = new ArrayList<Book>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The size
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
     *     The size
     */
    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    public Books withSize(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * 
     * @return
     *     The books
     */
    @JsonProperty("books")
    public List<Book> getBooks() {
        return books;
    }

    /**
     * 
     * @param books
     *     The books
     */
    @JsonProperty("books")
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Books withBooks(List<Book> books) {
        this.books = books;
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

    public Books withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}