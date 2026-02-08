package com.inventory.inventory_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Sale;

public interface SaleRepository extends MongoRepository<Sale, String> {
}
