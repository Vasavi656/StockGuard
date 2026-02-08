package com.inventory.inventory_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.ProductRepository;

@Controller
public class AnalyticsController {

    private final ProductRepository productRepository;

    public AnalyticsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/analytics")
    public String showAnalytics(Model model) {

        List<Product> products = productRepository.findAll();

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysLater = today.plusDays(7);

        List<String> stockLabels = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        List<Integer> stockValues = products.stream()
                .map(Product::getStock)
                .collect(Collectors.toList());

        long expiringSoon = products.stream()
                .filter(p -> p.getExpiryDate() != null)
                .filter(p ->
                        !p.getExpiryDate().isBefore(today) &&
                         p.getExpiryDate().isBefore(sevenDaysLater)
                )
                .count();

        long expired = products.stream()
                .filter(p -> p.getExpiryDate() != null)
                .filter(p -> p.getExpiryDate().isBefore(today))
                .count();

        long good = products.size() - expiringSoon - expired;

        model.addAttribute("stockLabels", stockLabels);
        model.addAttribute("stockValues", stockValues);
        model.addAttribute("expiryLabels",
                List.of("Expiring Soon", "Good", "Expired"));
        model.addAttribute("expiryValues",
                List.of((int) expiringSoon, (int) good, (int) expired));

        return "analytics";
    }
}
