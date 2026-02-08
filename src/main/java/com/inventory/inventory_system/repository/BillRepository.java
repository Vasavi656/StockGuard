package com.inventory.inventory_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Bill;

public interface BillRepository extends MongoRepository<Bill, String> {
}
