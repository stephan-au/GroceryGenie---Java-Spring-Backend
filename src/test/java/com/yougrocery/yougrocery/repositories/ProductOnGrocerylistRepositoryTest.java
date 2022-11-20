package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class ProductOnGrocerylistRepositoryTest {

    @Autowired
    private ProductOnGrocerylistRepository productOnGrocerylistRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private GrocerylistRepository grocerylistRepo;

    @Test
    void savingTheSameProductOnSameGrocerylist_throwsUniqueConstraint() {
        var product = new Product("Product name 1");
        var grocerylist = new Grocerylist("Grocerylist 1");
        ProductOnGrocerylist productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 5);
        ProductOnGrocerylist productOnGrocerylist2 = new ProductOnGrocerylist(2, product, grocerylist, 1);

        //act
        productRepo.save(product);
        grocerylistRepo.save(grocerylist);

        productOnGrocerylistRepo.save(productOnGrocerylist);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> productOnGrocerylistRepo.saveAndFlush(productOnGrocerylist2));
    }
}