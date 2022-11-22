package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import com.yougrocery.yougrocery.repositories.ProductOnGrocerylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOnGrocerylistService {
    private final ProductOnGrocerylistRepository productOnGrocerylistRepo;
    private final ProductService productService;
    private final GrocerylistService grocerylistService;

    public ProductOnGrocerylist addProductOnGrocerylist(String productName, int grocerylistId) {
        Product product = findOrCreateProduct(productName);
        Grocerylist grocerylist = grocerylistService.findById(grocerylistId);

        return productOnGrocerylistRepo.save(
                ProductOnGrocerylist.builder()
                        .product(product)
                        .groceryList(grocerylist)
                        .amount(1)
                        .build());
    }

    private Product findOrCreateProduct(String productName) {
        return productService.findOrCreateProduct(productName);
    }

    public List<ProductOnGrocerylist> findByGroceryListId(int groceryListId) {
        return productOnGrocerylistRepo.findByGroceryListId(groceryListId);
    }

    public void deleteById(int productOnGrocerylistId) {
        productOnGrocerylistRepo.deleteById(productOnGrocerylistId);
    }
}
