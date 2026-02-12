package com.inventory.inventory_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    private String Id;

    private String productId;

    private int quantitySold;

    private LocalDateTime soldAt;
}
