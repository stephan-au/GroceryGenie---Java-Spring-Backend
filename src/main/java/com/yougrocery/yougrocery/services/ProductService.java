package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(UUID id) {
        return productRepository.findById(id).get();
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
