package com.infointent.cassandradata.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReq {
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_price")
    private double productPrice;

    @JsonProperty("is_available")
    private boolean isAvailable;
}
