package com.inventory.inventory_system.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillItem {

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("quantity")
    private int quantity;

    private String productName;
    private double priceAtSale;
    private double itemTotal;
}
