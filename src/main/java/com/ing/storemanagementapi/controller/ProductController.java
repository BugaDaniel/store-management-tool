package com.ing.storemanagementapi.controller;

import com.ing.storemanagementapi.model.Product;
import com.ing.storemanagementapi.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/products/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productService.insertProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, @PathVariable Long id) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/decrement/{newQuantity}")
    public ResponseEntity<Product> decrementProductQuantity(@PathVariable Long id, @PathVariable @Min(1) int newQuantity) {
        Product updatedProduct = productService.changeProductQuantity(id, newQuantity*(-1));
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/increment/{newQuantity}")
    public ResponseEntity<Product> incrementProductQuantity(@PathVariable Long id, @PathVariable @Min(1) int newQuantity) {
        Product updatedProduct = productService.changeProductQuantity(id, newQuantity);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("Product with ID " + id + " deleted successfully.");
    }
}