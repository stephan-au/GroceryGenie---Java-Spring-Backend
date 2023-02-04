package com.yougrocery.yougrocery.grocerylist.services;

import com.yougrocery.yougrocery.grocerylist.models.GroceryItem;
import com.yougrocery.yougrocery.grocerylist.models.Product;
import com.yougrocery.yougrocery.grocerylist.repositories.GroceryItemRepository;
import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryItemService {
    private final GroceryItemRepository groceryItemRepo;
    private final ProductService productService;
    private final GrocerylistService grocerylistService;

    @Transactional
    public GroceryItem addGroceryItem(String productName, int grocerylistId) {
        Product product = findOrCreateProduct(productName);
        Grocerylist grocerylist = grocerylistService.findById(grocerylistId);

        return groceryItemRepo.save(
                GroceryItem.builder()
                        .product(product)
                        .groceryList(grocerylist)
                        .amount(1)
                        .build());
    }

    private Product findOrCreateProduct(String productName) {
        return productService.findOrCreateProduct(productName);
    }

    public List<GroceryItem> findByGroceryListId(int groceryListId) {
        return groceryItemRepo.findByGroceryListId(groceryListId);
    }

    public void deleteById(int groceryItemId) {
        groceryItemRepo.deleteById(groceryItemId);
    }
}
