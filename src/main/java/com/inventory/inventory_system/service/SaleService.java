package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.model.Sale;
import com.inventory.inventory_system.repository.ProductRepository;
import com.inventory.inventory_system.repository.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    public void processSale(Sale sale) {

    Product product = productRepository.findById(sale.getProductId())
        .orElseThrow(() -> new RuntimeException("Product not found"));

        
    if (product.getExpiryDate() != null &&
        product.getExpiryDate().isBefore(LocalDate.now())) {
        throw new RuntimeException(
            "Cannot sell expired product: " + product.getName()
        );
    }

    if (sale.getQuantitySold() <= 0 ||
        sale.getQuantitySold() > product.getStock()) {
        throw new RuntimeException("Invalid sale quantity");
    }

    sale.setSoldAt(LocalDateTime.now());
    saleRepository.save(sale);

    int updatedStock = product.getStock() - sale.getQuantitySold();
    product.setStock(updatedStock);
    productRepository.save(product);
}

}
