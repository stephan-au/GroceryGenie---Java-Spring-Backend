package com.yougrocery.yougrocery.grocerylist.repositories;

import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrocerylistRepository extends JpaRepository<Grocerylist, Integer> {
}