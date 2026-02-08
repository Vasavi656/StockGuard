package com.inventory.inventory_system.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.inventory_system.model.Bill;
import com.inventory.inventory_system.model.BillItem;
import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.model.Sale;
import com.inventory.inventory_system.repository.BillRepository;
import com.inventory.inventory_system.repository.ProductRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleService saleService;

    public Bill generateBill(List<BillItem> requestedItems) {


    for (BillItem req : requestedItems) {

        Product product = productRepository.findById(req.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (req.getQuantity() <= 0) {
            throw new RuntimeException("Invalid quantity for " + product.getName());
        }

        if (req.getQuantity() > product.getStock()) {
            throw new RuntimeException(
                "Insufficient stock for " + product.getName() +
                ". Available: " + product.getStock()
            );
        }
    }


    List<BillItem> finalItems = new ArrayList<>();
    double totalAmount = 0;

    for (BillItem req : requestedItems) {

        Product product = productRepository.findById(req.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        BillItem item = new BillItem();
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setPriceAtSale(product.getPrice());
        item.setQuantity(req.getQuantity());
        item.setItemTotal(product.getPrice() * req.getQuantity());

        
        Sale sale = new Sale();
        sale.setProductId(product.getId());
        sale.setQuantitySold(req.getQuantity());
        saleService.processSale(sale);

        totalAmount += item.getItemTotal();
        finalItems.add(item);
    }

    Bill bill = new Bill();
    bill.setItems(finalItems);
    bill.setTotalAmount(totalAmount);
    bill.setBillTime(LocalDateTime.now());

    return billRepository.save(bill);
}

}
