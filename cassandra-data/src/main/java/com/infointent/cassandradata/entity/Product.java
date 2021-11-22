package com.infointent.cassandradata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("t_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @PrimaryKeyColumn(name = "product_id")
    @JsonProperty("product_id")
    private String productId;

    @Column("product_name")
    @JsonProperty("product_name")
    private String productName;

    @Column("product_price")
    @JsonProperty("product_price")
    private double productPrice;

    @Column("is_available")
    @JsonProperty("is_available")
    private boolean isAvailable;

}
