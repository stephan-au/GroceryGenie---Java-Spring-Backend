package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import com.yougrocery.yougrocery.services.ProductOnGrocerylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product_on_grocerylist")
@RequiredArgsConstructor
public class ProductOnGrocerylistController {

    private final ProductOnGrocerylistService productOnGrocerylistService;

    @PostMapping("/grocerylist/{grocerylist_id}/product/{product_name}")
    public ResponseEntity<ProductOnGrocerylist> addProductToGrocerylist(
            @PathVariable("grocerylist_id") int grocerylistId,
            @PathVariable("product_name") String productName) {

        return new ResponseEntity<>(
                productOnGrocerylistService.addProductOnGrocerylist(productName, grocerylistId),
                HttpStatus.CREATED);
    }

    @GetMapping("/grocerylist/{grocerylist_id}")
    public ResponseEntity<List<ProductOnGrocerylist>> get(@PathVariable("grocerylist_id") int grocerylistId) {
        return ResponseEntity.ok(productOnGrocerylistService.findByGroceryListId(grocerylistId));
    }

//    @PutMapping
//    public ResponseEntity<Grocerylist> update(@RequestBody Grocerylist groceryList) {
//        return ResponseEntity.ok(productOnGrocerylistService.save(groceryList));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Integer> delete(@PathVariable("id") int id) {
//        productOnGrocerylistService.delete(id);
//
//        return ResponseEntity.ok(id);
//    }
}
