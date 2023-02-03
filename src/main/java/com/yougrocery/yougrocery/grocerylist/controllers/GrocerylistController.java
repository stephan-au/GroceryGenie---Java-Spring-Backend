package com.yougrocery.yougrocery.grocerylist.controllers;

import com.yougrocery.yougrocery.grocerylist.services.GrocerylistService;
import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grocerylist")
@RequiredArgsConstructor
@CrossOrigin
public class GrocerylistController {

    private final GrocerylistService grocerylistService;

    @PostMapping
    public ResponseEntity<Grocerylist> create(@RequestBody Grocerylist groceryList) {
        return new ResponseEntity<>(
                grocerylistService.save(groceryList),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Grocerylist>> getAll() {
        return ResponseEntity.ok(grocerylistService.findAll());
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
