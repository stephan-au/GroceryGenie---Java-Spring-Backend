package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.repositories.ProductOnGrocerylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOnGrocerylistService {
    private final ProductOnGrocerylistRepository productOnGrocerylistRepository;


}
