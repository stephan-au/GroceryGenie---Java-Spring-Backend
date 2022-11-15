package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.Grocerylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrocerylistRepository extends JpaRepository<Grocerylist, Long> {
}