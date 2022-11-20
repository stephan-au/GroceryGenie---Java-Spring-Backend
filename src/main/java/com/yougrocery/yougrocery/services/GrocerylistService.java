package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.repositories.GrocerylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class GrocerylistService {

    private final GrocerylistRepository grocerylistRepository;

    public Grocerylist save(Grocerylist groceryList) {
        return grocerylistRepository.save(groceryList);
    }

    public Grocerylist findById(int id) {
        return grocerylistRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Grocerylist with id: " + id + " doesn't exist"));
    }

    public void delete(int id) {
        grocerylistRepository.deleteById(id);
    }
}
