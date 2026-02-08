package com.inventory.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Bill;
import com.inventory.inventory_system.model.BillItem;
import com.inventory.inventory_system.repository.BillRepository;
import com.inventory.inventory_system.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billrepository;

   @PostMapping
    public ResponseEntity<Bill> generateBill(@RequestBody List<BillItem> items) {
        Bill bill = billService.generateBill(items);
        return ResponseEntity.ok(bill);
    }
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills(){
        return ResponseEntity.ok(billrepository.findAll());
    }

}
