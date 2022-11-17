package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.repositories.GrocerylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GrocerylistService {

    private final GrocerylistRepository grocerylistRepository;

    public Grocerylist save(Grocerylist groceryList) {
        return grocerylistRepository.save(groceryList);
    }

    //TODO nice error if not exist
    public Grocerylist findById(int id) {
        return grocerylistRepository.findById(id).get();
    }

    public void delete(int id) {
        grocerylistRepository.deleteById(id);
    }
}
