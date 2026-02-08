package com.inventory.inventory_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.ProductRepository;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") 
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/test")
    public List<Product> testFetch() {
        List<Product> products = productRepository.findAll();
        System.out.println("Fetched: " + products.size());
        products.forEach(System.out::println);
        return products;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    } 
    @PostMapping
    public ResponseEntity<String> postProduct(@RequestBody Product prod){
        LocalDate today = LocalDate.now();
        if(prod.getExpiryDate() != null && !prod.getExpiryDate().isAfter(today)) {
            return ResponseEntity
                    .badRequest()
                    .body("Cannot add expired product.");
        }

        productRepository.save(prod);
        return ResponseEntity.ok("Product added successfully.");
    }

    @PostMapping("/bulk")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return productRepository.saveAll(products);
    }
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id){
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Product putProduct(@PathVariable String id, @RequestBody Product updatedProduct){
        return productRepository.findById(id).map(prod -> {
            prod.setName(updatedProduct.getName());
            prod.setCategory(updatedProduct.getCategory());
            prod.setPrice(updatedProduct.getPrice());
            prod.setMinStockLevel(updatedProduct.getMinStockLevel());
            return productRepository.save(prod);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/category")
    public List<Product> getByCategory(@RequestParam String category) {
        return productRepository.findByCategory(category);
    }

    @GetMapping("/price-range")
    public List<Product> getByPriceRange(@RequestParam double min, @RequestParam double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productRepository.findByStockLessThanEqual(5);
    }

    @GetMapping("/expiring-soon")
public List<Product> getExpiringSoon(@RequestParam(defaultValue = "7") int days) {

    LocalDate today = LocalDate.now();
    LocalDate threshold = today.plusDays(days);

    return productRepository.findAll().stream()
            .filter(p -> p.getExpiryDate() != null)
            .filter(p -> 
                p.getExpiryDate().isBefore(today) ||                     
                ( !p.getExpiryDate().isBefore(today) &&                  
                  p.getExpiryDate().isBefore(threshold) )                 
            )
            .toList();
}
}
