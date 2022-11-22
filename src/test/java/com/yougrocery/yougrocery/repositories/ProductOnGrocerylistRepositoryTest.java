package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        //Arrange
        var product = new Product("Product name 1");
        var grocerylist = new Grocerylist("Grocerylist 1");
        ProductOnGrocerylist productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 5);
        ProductOnGrocerylist productOnGrocerylist2 = new ProductOnGrocerylist(2, product, grocerylist, 1);

        //act
        productRepo.save(product);
        grocerylistRepo.save(grocerylist);

        //Assert
        productOnGrocerylistRepo.save(productOnGrocerylist);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> productOnGrocerylistRepo.saveAndFlush(productOnGrocerylist2));
    }

    @Test
    void twoProductsOnGrocerylist_findProductsOnGrocerylistByGrocerylistId_returnsTwoProductsOnGrocerylist() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var grocerylist = new Grocerylist("Grocerylist 1");
        productRepo.saveAll(List.of(product, product2));
        grocerylistRepo.save(grocerylist);
        productOnGrocerylistRepo.saveAll(List.of(
                new ProductOnGrocerylist(1, product, grocerylist, 1),
                new ProductOnGrocerylist(2, product2, grocerylist, 1)));

        //Act
        List<ProductOnGrocerylist> actualProductsOnGrocerylist = findProductsOnGrocerylist(grocerylist);

        //Assert
        assertEquals(2, actualProductsOnGrocerylist.size());
        assertThat(actualProductsOnGrocerylist.get(0))
                .hasProduct(product)
                .hasGroceryList(grocerylist)
                .hasAmount(1);
        assertThat(actualProductsOnGrocerylist.get(1))
                .hasProduct(product2)
                .hasGroceryList(grocerylist)
                .hasAmount(1);
    }

    @Test
    void multipleProductsOnMultipleGrocerylist_findProductsOnEachGrocerylistByGrocerylistId_returnsCorrectProductsOnGrocerylist() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var product3 = new Product("Product name 3");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var grocerylist2 = new Grocerylist("Grocerylist 2");

        productRepo.saveAll(List.of(product, product2, product3));
        grocerylistRepo.saveAll(List.of(grocerylist, grocerylist2));
        productOnGrocerylistRepo.saveAllAndFlush(List.of(
                new ProductOnGrocerylist(1, product, grocerylist, 1),
                new ProductOnGrocerylist(2, product2, grocerylist, 2),
                new ProductOnGrocerylist(3, product3, grocerylist2, 1),
                new ProductOnGrocerylist(4, product2, grocerylist2, 4)));

        //Act
        List<ProductOnGrocerylist> actualProductsOnGrocerylist = findProductsOnGrocerylist(grocerylist);
        List<ProductOnGrocerylist> actualProductsOnGrocerylist2 = findProductsOnGrocerylist(grocerylist2);
        productOnGrocerylistRepo.flush();

        //Assert
        assertEquals(2, actualProductsOnGrocerylist.size());
        assertThat(actualProductsOnGrocerylist.get(0))
                .hasProduct(product)
                .hasGroceryList(grocerylist)
                .hasAmount(1);
        assertThat(actualProductsOnGrocerylist.get(1))
                .hasProduct(product2)
                .hasGroceryList(grocerylist)
                .hasAmount(2);

        assertEquals(2, actualProductsOnGrocerylist2.size());
        assertThat(actualProductsOnGrocerylist2.get(0))
                .hasProduct(product3)
                .hasGroceryList(grocerylist2)
                .hasAmount(1);
        assertThat(actualProductsOnGrocerylist2.get(1))
                .hasProduct(product2)
                .hasGroceryList(grocerylist2)
                .hasAmount(4);
    }

    private List<ProductOnGrocerylist> findProductsOnGrocerylist(Grocerylist grocerylist) {
        return productOnGrocerylistRepo.findByGroceryListId(grocerylist.getId());
    }
}