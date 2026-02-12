package com.inventory.inventory_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="transactions")
@Data
public class Transaction {
    @Id
    private String id;
    private String saleId;
    private String token;
    private String status;
    private int tokenAmount;
    private LocalDateTime timestamp;
    public Transaction(){}
    public Transaction(String saleId, String token, String status, int tokenAmount) {
        this.saleId = saleId;
        this.token = token;
        this.status = status;
        this.tokenAmount = tokenAmount;
        this.timestamp = LocalDateTime.now();
    }
}
