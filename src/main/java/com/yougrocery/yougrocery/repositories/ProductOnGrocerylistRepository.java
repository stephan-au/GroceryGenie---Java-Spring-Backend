package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductOnGrocerylistRepository extends JpaRepository<ProductOnGrocerylist, UUID> {
}