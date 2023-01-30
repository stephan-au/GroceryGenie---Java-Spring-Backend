package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.GroceryItem;
import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.repositories.GroceryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroceryItemService {
    private final GroceryItemRepository groceryItemRepo;
    private final ProductService productService;
    private final GrocerylistService grocerylistService;

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
