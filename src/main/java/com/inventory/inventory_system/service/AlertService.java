package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.inventory_system.model.Alert;
import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.AlertRepository;
import com.inventory.inventory_system.repository.ProductRepository;

@Service
public class AlertService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AlertRepository alertRepository;

    public void checkAlerts() {

        List<Product> products = productRepository.findAll();

        for (Product p : products) {

            if (p.getStock() <= p.getMinStockLevel()) {

                String msg = "Low stock: " + p.getName() +
                        " has only " + p.getStock() + " items left.";

                if (!alertRepository.existsByMessage(msg)) {
                    alertRepository.save(new Alert(msg));
                }
            }

            if (p.getExpiryDate() != null &&
                !p.getExpiryDate().isBefore(LocalDate.now()) &&
                !p.getExpiryDate().isAfter(LocalDate.now().plusDays(7))) {

                String msg = "Expiring soon: " + p.getName() +
                        " expires on " + p.getExpiryDate();

                if (!alertRepository.existsByMessage(msg)) {
                    alertRepository.save(new Alert(msg));
                }
            }
        }
    }
}
