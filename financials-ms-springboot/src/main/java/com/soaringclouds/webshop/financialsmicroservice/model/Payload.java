package com.soaringclouds.webshop.financialsmicroservice.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "orderIdentifier", "nameAddressee", "destination", "shippingMethod", "giftWrapping",
		"personalMessage", "items", "shippingId", "shippingStatus", "auditTrail", "shippingCosts",
		"submissionDate", "parcels" })
public class Payload {

    @JsonProperty("orderIdentifier") private String orderIdentifier;
    @JsonProperty("nameAddressee") private String nameAddressee;
    @JsonProperty("destination") private Destination destination;
    @JsonProperty("shippingMethod") private String shippingMethod;
    @JsonProperty("giftWrapping") private Boolean giftWrapping;
    @JsonProperty("personalMessage") private String personalMessage;
    @JsonProperty("items") private List<Item> items = null;
    @JsonProperty("shippingId") private String shippingId;
    @JsonProperty("shippingStatus") private String shippingStatus;
    @JsonProperty("auditTrail") private List<AuditTrail> auditTrail = null;
    @JsonProperty("shippingCosts") private Integer shippingCosts;
    @JsonProperty("submissionDate") private String submissionDate;
    @JsonProperty("parcels") private List<Parcel> parcels = null;
    @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("orderIdentifier")
    public String getOrderIdentifier() {
	return orderIdentifier;
    }

    @JsonProperty("orderIdentifier")
    public void setOrderIdentifier(String orderIdentifier) {
	this.orderIdentifier = orderIdentifier;
    }

    public Payload withOrderIdentifier(String orderIdentifier) {
	this.orderIdentifier = orderIdentifier;
	return this;
    }

    @JsonProperty("nameAddressee")
    public String getNameAddressee() {
	return nameAddressee;
    }

    @JsonProperty("nameAddressee")
    public void setNameAddressee(String nameAddressee) {
	this.nameAddressee = nameAddressee;
    }

    public Payload withNameAddressee(String nameAddressee) {
	this.nameAddressee = nameAddressee;
	return this;
    }

    @JsonProperty("destination")
    public Destination getDestination() {
	return destination;
    }

    @JsonProperty("destination")
    public void setDestination(Destination destination) {
	this.destination = destination;
    }

    public Payload withDestination(Destination destination) {
	this.destination = destination;
	return this;
    }

    @JsonProperty("shippingMethod")
    public String getShippingMethod() {
	return shippingMethod;
    }

    @JsonProperty("shippingMethod")
    public void setShippingMethod(String shippingMethod) {
	this.shippingMethod = shippingMethod;
    }

    public Payload withShippingMethod(String shippingMethod) {
	this.shippingMethod = shippingMethod;
	return this;
    }

    @JsonProperty("giftWrapping")
    public Boolean getGiftWrapping() {
	return giftWrapping;
    }

    @JsonProperty("giftWrapping")
    public void setGiftWrapping(Boolean giftWrapping) {
	this.giftWrapping = giftWrapping;
    }

    public Payload withGiftWrapping(Boolean giftWrapping) {
	this.giftWrapping = giftWrapping;
	return this;
    }

    @JsonProperty("personalMessage")
    public String getPersonalMessage() {
	return personalMessage;
    }

    @JsonProperty("personalMessage")
    public void setPersonalMessage(String personalMessage) {
	this.personalMessage = personalMessage;
    }

    public Payload withPersonalMessage(String personalMessage) {
	this.personalMessage = personalMessage;
	return this;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
	return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
	this.items = items;
    }

    public Payload withItems(List<Item> items) {
	this.items = items;
	return this;
    }

    @JsonProperty("shippingId")
    public String getShippingId() {
	return shippingId;
    }

    @JsonProperty("shippingId")
    public void setShippingId(String shippingId) {
	this.shippingId = shippingId;
    }

    public Payload withShippingId(String shippingId) {
	this.shippingId = shippingId;
	return this;
    }

    @JsonProperty("shippingStatus")
    public String getShippingStatus() {
	return shippingStatus;
    }

    @JsonProperty("shippingStatus")
    public void setShippingStatus(String shippingStatus) {
	this.shippingStatus = shippingStatus;
    }

    public Payload withShippingStatus(String shippingStatus) {
	this.shippingStatus = shippingStatus;
	return this;
    }

    @JsonProperty("auditTrail")
    public List<AuditTrail> getAuditTrail() {
	return auditTrail;
    }

    @JsonProperty("auditTrail")
    public void setAuditTrail(List<AuditTrail> auditTrail) {
	this.auditTrail = auditTrail;
    }

    public Payload withAuditTrail(List<AuditTrail> auditTrail) {
	this.auditTrail = auditTrail;
	return this;
    }

    @JsonProperty("shippingCosts")
    public Integer getShippingCosts() {
	return shippingCosts;
    }

    @JsonProperty("shippingCosts")
    public void setShippingCosts(Integer shippingCosts) {
	this.shippingCosts = shippingCosts;
    }

    public Payload withShippingCosts(Integer shippingCosts) {
	this.shippingCosts = shippingCosts;
	return this;
    }

    @JsonProperty("submissionDate")
    public String getSubmissionDate() {
	return submissionDate;
    }

    @JsonProperty("submissionDate")
    public void setSubmissionDate(String submissionDate) {
	this.submissionDate = submissionDate;
    }

    public Payload withSubmissionDate(String submissionDate) {
	this.submissionDate = submissionDate;
	return this;
    }

    @JsonProperty("parcels")
    public List<Parcel> getParcels() {
	return parcels;
    }

    @JsonProperty("parcels")
    public void setParcels(List<Parcel> parcels) {
	this.parcels = parcels;
    }

    public Payload withParcels(List<Parcel> parcels) {
	this.parcels = parcels;
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

    public Payload withAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	return this;
    }
}
