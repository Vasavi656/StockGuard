package com.inventory.inventory_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    private String id;

    private String message;

    private LocalDateTime createdAt;

    private boolean read;

    public Alert(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }
}
