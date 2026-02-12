package com.inventory.inventory_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction,String>{

}
