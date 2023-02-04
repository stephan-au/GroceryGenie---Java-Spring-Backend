package com.yougrocery.yougrocery.grocerylist.services;

import com.yougrocery.yougrocery.grocerylist.models.Product;
import com.yougrocery.yougrocery.grocerylist.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findOrCreateProduct(String productName) {
        return findByName(productName)
                .orElseGet(() -> save(new Product(productName)));
    }

    private Optional<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
