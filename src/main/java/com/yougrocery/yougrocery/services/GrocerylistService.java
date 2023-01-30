package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.repositories.GrocerylistRepository;
import com.yougrocery.yougrocery.repositories.ProductOnGrocerylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class GrocerylistService {

    private final GrocerylistRepository grocerylistRepository;
    private final ProductOnGrocerylistRepository productOnGrocerylistRepo;

    public Grocerylist save(Grocerylist groceryList) {
        return grocerylistRepository.save(groceryList);
    }

    public List<Grocerylist> findAll() {
        return grocerylistRepository.findAll();
    }

    public Grocerylist findById(int id) {
        return grocerylistRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Grocerylist with id: " + id + " doesn't exist"));
    }

    public void delete(int id) {
        productOnGrocerylistRepo.deleteByGroceryListId(id);
        grocerylistRepository.deleteById(id);
    }
}
