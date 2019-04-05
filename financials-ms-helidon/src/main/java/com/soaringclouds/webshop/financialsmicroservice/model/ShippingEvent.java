package com.soaringclouds.webshop.financialsmicroservice.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "eventType", "payload", "module", "transactionIdentifier", "timestamp" })
public class ShippingEvent {

    @JsonProperty("eventType") private String eventType;
    @JsonProperty("payload") private Payload payload;
    @JsonProperty("module") private String module;
    @JsonProperty("transactionIdentifier") private String transactionIdentifier;
    @JsonProperty("timestamp") private String timestamp;
    @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("eventType")
    public String getEventType() {
	return eventType;
    }

    @JsonProperty("eventType")
    public void setEventType(String eventType) {
	this.eventType = eventType;
    }

    public ShippingEvent withEventType(String eventType) {
	this.eventType = eventType;
	return this;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
	return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
	this.payload = payload;
    }

    public ShippingEvent withPayload(Payload payload) {
	this.payload = payload;
	return this;
    }

    @JsonProperty("module")
    public String getModule() {
	return module;
    }

    @JsonProperty("module")
    public void setModule(String module) {
	this.module = module;
    }

    public ShippingEvent withModule(String module) {
	this.module = module;
	return this;
    }

    @JsonProperty("transactionIdentifier")
    public String getTransactionIdentifier() {
	return transactionIdentifier;
    }

    @JsonProperty("transactionIdentifier")
    public void setTransactionIdentifier(String transactionIdentifier) {
	this.transactionIdentifier = transactionIdentifier;
    }

    public ShippingEvent withTransactionIdentifier(String transactionIdentifier) {
	this.transactionIdentifier = transactionIdentifier;
	return this;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
	return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
    }

    public ShippingEvent withTimestamp(String timestamp) {
	this.timestamp = timestamp;
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

    public ShippingEvent withAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	return this;
    }

}
