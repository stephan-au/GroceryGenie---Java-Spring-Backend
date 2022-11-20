package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import com.yougrocery.yougrocery.services.ProductOnGrocerylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/{id}")
//    public ResponseEntity<Grocerylist> get(@PathVariable("id") int id) {
//        return ResponseEntity.ok(grocerylistService.findById(id));
//    }
//
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
