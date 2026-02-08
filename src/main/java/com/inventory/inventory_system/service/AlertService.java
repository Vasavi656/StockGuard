package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkAlerts() {

        System.out.println("⏰ Alert job started at 10 PM");

        List<Product> lowStock = productRepository.findAll().stream()
                .filter(p -> p.getStock() <= p.getMinStockLevel())
                .toList();

        List<Product> expiringSoon = productRepository.findAll().stream()
                .filter(p -> p.getExpiryDate() != null)
                .filter(p -> !p.getExpiryDate().isBefore(LocalDate.now()))
                .filter(p -> !p.getExpiryDate().isAfter(LocalDate.now().plusDays(7)))
                .toList();

        for (Product p : lowStock) {
            String msg = "⚠ Low stock: " + p.getName() +
                         " has only " + p.getStock() + " items left!";
            if (!alertRepository.existsByMessage(msg)) {
                alertRepository.save(new Alert(msg));
            }
        }

        for (Product p : expiringSoon) {
            String msg = "⏳ Expiring soon: " + p.getName() +
                         " expires on " + p.getExpiryDate();
            if (!alertRepository.existsByMessage(msg)) {
                alertRepository.save(new Alert(msg));
            }
        }

        System.out.println("✅ Alert job finished");
    }
}
