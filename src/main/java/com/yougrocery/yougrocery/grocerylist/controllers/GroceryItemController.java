package com.yougrocery.yougrocery.grocerylist.controllers;

import com.yougrocery.yougrocery.grocerylist.models.GroceryItem;
import com.yougrocery.yougrocery.grocerylist.services.GroceryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grocery_item")
@RequiredArgsConstructor
public class GroceryItemController {

    private final GroceryItemService groceryItemService;

    @PostMapping("/grocerylist/{grocerylist_id}/product/{product_name}")
    public ResponseEntity<GroceryItem> addProductToGrocerylist(
            @PathVariable("grocerylist_id") int grocerylistId,
            @PathVariable("product_name") String productName) {

        return new ResponseEntity<>(
                groceryItemService.addGroceryItem(productName, grocerylistId),
                HttpStatus.CREATED);
    }

    @GetMapping("/grocerylist/{grocerylist_id}")
    public ResponseEntity<List<GroceryItem>> getGroceryItems(
            @PathVariable("grocerylist_id") int grocerylistId) {

        return ResponseEntity.ok(groceryItemService.findByGroceryListId(grocerylistId));
    }

//    @PutMapping
//    public ResponseEntity<Grocerylist> update(@RequestBody Grocerylist groceryList) {
//        return ResponseEntity.ok(groceryItemService.save(groceryList));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") int groceryItemId) {
        groceryItemService.deleteById(groceryItemId);

        return ResponseEntity.ok(groceryItemId);
    }
}
