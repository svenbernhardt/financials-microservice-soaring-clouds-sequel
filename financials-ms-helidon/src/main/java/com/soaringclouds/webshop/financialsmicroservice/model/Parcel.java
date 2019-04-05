
package com.soaringclouds.webshop.financialsmicroservice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "parcelDeliveryService",
    "trackAndTraceIdentifier",
    "parcelLogItems"
})
public class Parcel {

    @JsonProperty("parcelDeliveryService")
    private String parcelDeliveryService;
    @JsonProperty("trackAndTraceIdentifier")
    private String trackAndTraceIdentifier;
    @JsonProperty("parcelLogItems")
    private List<ParcelLogItem> parcelLogItems = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("parcelDeliveryService")
    public String getParcelDeliveryService() {
        return parcelDeliveryService;
    }

    @JsonProperty("parcelDeliveryService")
    public void setParcelDeliveryService(String parcelDeliveryService) {
        this.parcelDeliveryService = parcelDeliveryService;
    }

    public Parcel withParcelDeliveryService(String parcelDeliveryService) {
        this.parcelDeliveryService = parcelDeliveryService;
        return this;
    }

    @JsonProperty("trackAndTraceIdentifier")
    public String getTrackAndTraceIdentifier() {
        return trackAndTraceIdentifier;
    }

    @JsonProperty("trackAndTraceIdentifier")
    public void setTrackAndTraceIdentifier(String trackAndTraceIdentifier) {
        this.trackAndTraceIdentifier = trackAndTraceIdentifier;
    }

    public Parcel withTrackAndTraceIdentifier(String trackAndTraceIdentifier) {
        this.trackAndTraceIdentifier = trackAndTraceIdentifier;
        return this;
    }

    @JsonProperty("parcelLogItems")
    public List<ParcelLogItem> getParcelLogItems() {
        return parcelLogItems;
    }

    @JsonProperty("parcelLogItems")
    public void setParcelLogItems(List<ParcelLogItem> parcelLogItems) {
        this.parcelLogItems = parcelLogItems;
    }

    public Parcel withParcelLogItems(List<ParcelLogItem> parcelLogItems) {
        this.parcelLogItems = parcelLogItems;
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

    public Parcel withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
