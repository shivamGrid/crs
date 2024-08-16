package com.retail.search.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCatalogDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("primary_product_id")
    private String primaryProductId; // Maps to "primary_product_id" in JSON

    @JsonProperty("gtin")
    private String gtin;

    @JsonProperty("categories")
    private List<String> categories;
//
    @JsonProperty("title")
    private String title;

    @JsonProperty("brands")
    private List<String> brands;

//    @JsonProperty("description")
//    private String description;
//
//    @JsonProperty("language_code")
//    private String languageCode;

//    @JsonProperty("attributes")
//    private Map<String, Attribute> attributes;

//    @JsonProperty("tags")
//    private List<String> tags;
//
//    @JsonProperty("price_info")
//    private PriceInfo priceInfo;
//
//    @JsonProperty("rating")
//    private Rating rating;
//
//    @JsonProperty("available_time")
//    private String availableTime;
//
//    @JsonProperty("availability")
//    private String availability;
//
//    @JsonProperty("available_quantity")
//    private int availableQuantity;
//
//    @JsonProperty("fulfillment_info")
//    private List<FulfillmentInfo> fulfillmentInfo;
//
//    @JsonProperty("uri")
//    private String uri;
//
//    @JsonProperty("images")
//    private List<Image> images;
//
//    @JsonProperty("audience")
//    private Audience audience;
//
//    @JsonProperty("color_info")
//    private ColorInfo colorInfo;
//
//    @JsonProperty("sizes")
//    private List<String> sizes;
//
//    @JsonProperty("materials")
//    private List<String> materials;
//
//    @JsonProperty("patterns")
//    private List<String> patterns;
//
//    @JsonProperty("conditions")
//    private List<String> conditions;
//
//    @JsonProperty("publish_time")
//    private String publishTime;
//
//    @JsonProperty("expireTime")
//    private String expireTime;
}
