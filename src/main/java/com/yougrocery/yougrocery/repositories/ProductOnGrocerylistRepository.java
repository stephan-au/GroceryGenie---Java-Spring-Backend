package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOnGrocerylistRepository extends JpaRepository<ProductOnGrocerylist, Integer> {
    List<ProductOnGrocerylist> findByGroceryListId(int id);
}