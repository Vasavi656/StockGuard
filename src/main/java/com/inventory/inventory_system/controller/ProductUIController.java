package com.inventory.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.ProductRepository;

@Controller
public class ProductUIController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";  
    }

    @PostMapping("/products/add")
    public String addProduct(Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable String id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "edit_product";  
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable String id, Product updatedProduct) {
        productRepository.findById(id).ifPresent(prod -> {
            prod.setName(updatedProduct.getName());
            prod.setCategory(updatedProduct.getCategory());
            prod.setPrice(updatedProduct.getPrice());
            // prod.setStock(updatedProduct.getStock());
            prod.setMinStockLevel(updatedProduct.getMinStockLevel());
            prod.setExpiryDate(updatedProduct.getExpiryDate());
            productRepository.save(prod);
        });
        return "redirect:/products";
    }
}
