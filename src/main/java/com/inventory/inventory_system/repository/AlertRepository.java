package com.inventory.inventory_system.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.inventory.inventory_system.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String> {

    boolean existsByMessage(String message);

    List<Alert> findAllByOrderByCreatedAtDesc();

    List<Alert> findByReadFalseOrderByCreatedAtDesc();
}
