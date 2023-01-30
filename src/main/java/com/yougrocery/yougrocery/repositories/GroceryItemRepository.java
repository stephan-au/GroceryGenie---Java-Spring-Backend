package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Integer> {
    List<GroceryItem> findByGroceryListId(int id);

    void deleteByGroceryListId(int id);

}