package com.infointent.cassandrareactive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("t_product_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @PrimaryKey
    @JsonProperty("product_id")
    private UUID Id;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("product_price")
    private double price;

    @JsonProperty("product_count")
    private int count;


}
