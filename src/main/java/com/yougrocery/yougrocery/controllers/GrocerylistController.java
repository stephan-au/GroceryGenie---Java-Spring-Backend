package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.services.GrocerylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grocerylist")
@RequiredArgsConstructor
public class GrocerylistController {

    private final GrocerylistService grocerylistService;

    @PostMapping
    public ResponseEntity<Grocerylist> create(@RequestBody Grocerylist groceryList) {
        return new ResponseEntity<>(
                grocerylistService.save(groceryList),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grocerylist> get(@PathVariable("id") int id) {
        return ResponseEntity.ok(grocerylistService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Grocerylist> update(@RequestBody Grocerylist groceryList) {
        return ResponseEntity.ok(grocerylistService.save(groceryList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") int id) {
        grocerylistService.delete(id);

        return ResponseEntity.ok(id);
    }
}
