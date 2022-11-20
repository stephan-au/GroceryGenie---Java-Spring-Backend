package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductOnGrocerylistRepository extends JpaRepository<ProductOnGrocerylist, Integer> {
    List<ProductOnGrocerylist> findByGroceryList_Id(int id);
}