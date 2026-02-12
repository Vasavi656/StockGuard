package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.model.Sale;
import com.inventory.inventory_system.model.Transaction;
import com.inventory.inventory_system.repository.ProductRepository;
import com.inventory.inventory_system.repository.SaleRepository;
import com.inventory.inventory_system.repository.TransactionRepository;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    public String processSale(Sale sale) {
            Product product = productRepository.findById(sale.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

                
         System.out.println("Processing sale...");
System.out.println("Product found: " + product.getName());
                
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
            Sale savedSale=saleRepository.save(sale);

            String token=UUID.randomUUID().toString();
            String status = Math.random() < 0.8 ? "SUCCESS" : "FAILED";

            if(status.equals("SUCCESS")){
                int updatedStock = product.getStock() - sale.getQuantitySold();
                product.setStock(updatedStock);
                productRepository.save(product);
            }
            Transaction transaction=
            new Transaction(savedSale.getId(),token,status,sale.getQuantitySold());
            transactionRepository.save(transaction);
            return "Sale processed. Transaction status: " + status;

        }

}
