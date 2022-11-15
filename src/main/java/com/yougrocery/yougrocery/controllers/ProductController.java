package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return new ResponseEntity<>(
                productService.save(product),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> delete(@PathVariable("id") UUID id) {
        productService.delete(id);

        return ResponseEntity.ok(id);
    }
}
