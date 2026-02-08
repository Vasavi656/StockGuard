package com.inventory.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Sale;
import com.inventory.inventory_system.repository.SaleRepository;
import com.inventory.inventory_system.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @PostMapping
    public ResponseEntity<String> recordSale(@RequestBody Sale sale) {
        saleService.processSale(sale);
        return ResponseEntity.ok("Sale recorded successfully");
    }

    @GetMapping
    public ResponseEntity<List<Sale>> showSales() {
        return ResponseEntity.ok(saleRepository.findAll());
    }
}
