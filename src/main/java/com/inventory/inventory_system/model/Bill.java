package com.inventory.inventory_system.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("bills")
@Data
@NoArgsConstructor
public class Bill {

    @Id
    private String id;

    private LocalDateTime billTime;

    private List<BillItem> items;

    private double totalAmount;
}
