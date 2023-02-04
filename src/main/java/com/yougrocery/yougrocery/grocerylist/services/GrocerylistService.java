package com.yougrocery.yougrocery.grocerylist.services;

import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import com.yougrocery.yougrocery.grocerylist.repositories.GroceryItemRepository;
import com.yougrocery.yougrocery.grocerylist.repositories.GrocerylistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GrocerylistService {

    private final GrocerylistRepository grocerylistRepo;
    private final GroceryItemRepository groceryItemRepo;

    public Grocerylist save(Grocerylist groceryList) {
        return grocerylistRepo.save(groceryList);
    }

    public List<Grocerylist> findAll() {
        return grocerylistRepo.findAll();
    }

    public Grocerylist findById(int id) {
        return grocerylistRepo
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Grocerylist with id: " + id + " doesn't exist"));
    }

    @Transactional
    public void delete(int id) {
        groceryItemRepo.deleteByGroceryListId(id);
        grocerylistRepo.deleteById(id);
    }
}
