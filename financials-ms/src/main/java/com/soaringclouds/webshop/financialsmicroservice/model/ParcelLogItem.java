
package com.soaringclouds.webshop.financialsmicroservice.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "location",
    "parcelStatus",
    "parcelLogTimestamp"
})
public class ParcelLogItem {

    @JsonProperty("location")
    private String location;
    @JsonProperty("parcelStatus")
    private String parcelStatus;
    @JsonProperty("parcelLogTimestamp")
    private String parcelLogTimestamp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    public ParcelLogItem withLocation(String location) {
        this.location = location;
        return this;
    }

    @JsonProperty("parcelStatus")
    public String getParcelStatus() {
        return parcelStatus;
    }

    @JsonProperty("parcelStatus")
    public void setParcelStatus(String parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    public ParcelLogItem withParcelStatus(String parcelStatus) {
        this.parcelStatus = parcelStatus;
        return this;
    }

    @JsonProperty("parcelLogTimestamp")
    public String getParcelLogTimestamp() {
        return parcelLogTimestamp;
    }

    @JsonProperty("parcelLogTimestamp")
    public void setParcelLogTimestamp(String parcelLogTimestamp) {
        this.parcelLogTimestamp = parcelLogTimestamp;
    }

    public ParcelLogItem withParcelLogTimestamp(String parcelLogTimestamp) {
        this.parcelLogTimestamp = parcelLogTimestamp;
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

    public ParcelLogItem withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
