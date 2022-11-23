package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
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
        var productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 5);
        var productOnGrocerylist2 = new ProductOnGrocerylist(2, product, grocerylist, 1);

        //act
        saveProducts(product);
        saveGrocerylist(grocerylist);

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
        var productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 1);
        var productOnGrocerylist2 = new ProductOnGrocerylist(2, product2, grocerylist, 1);

        saveAllProducts(List.of(product, product2));
        saveGrocerylist(grocerylist);
        saveAllProductsOnGrocerylist(List.of(productOnGrocerylist, productOnGrocerylist2));

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
        var productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 1);
        var productOnGrocerylist1 = new ProductOnGrocerylist(2, product2, grocerylist, 2);
        var productOnGrocerylist2 = new ProductOnGrocerylist(3, product3, grocerylist2, 1);
        var productOnGrocerylist3 = new ProductOnGrocerylist(4, product2, grocerylist2, 4);

        saveAllProducts(List.of(product, product2, product3));
        saveAllGrocerylists(List.of(grocerylist, grocerylist2));
        saveAllProductsOnGrocerylist(List.of(
                productOnGrocerylist,
                productOnGrocerylist1,
                productOnGrocerylist2,
                productOnGrocerylist3));

        //Act
        List<ProductOnGrocerylist> actualProductsOnGrocerylist = findProductsOnGrocerylist(grocerylist);
        List<ProductOnGrocerylist> actualProductsOnGrocerylist2 = findProductsOnGrocerylist(grocerylist2);

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

    @Test
    void twoProductsOnGrocerylist_deleteProductsOnGrocerylistByGrocerylistId_deletesProductsOnGrocerylist() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 1);
        var productOnGrocerylist2 = new ProductOnGrocerylist(2, product2, grocerylist, 1);

        saveAllProducts(List.of(product, product2));
        saveGrocerylist(grocerylist);
        saveAllProductsOnGrocerylist(List.of(productOnGrocerylist, productOnGrocerylist2));

        //Act
        deleteProductsOnGrocerylistByGrocerylistId(grocerylist);

        //Assert
        assertEquals(0, findAllProductsOnGrocerylists().size());
    }

    @Test
    void multipleProductsOnMultipleGrocerylist_deleteProductsFromGrocerylist1_onlyDeletesProductsFromGrocerylist1() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var product3 = new Product("Product name 3");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var grocerylist2 = new Grocerylist("Grocerylist 2");
        var productOnGrocerylist = new ProductOnGrocerylist(1, product, grocerylist, 1);
        var productOnGrocerylist1 = new ProductOnGrocerylist(2, product2, grocerylist, 2);
        var productOnGrocerylist2 = new ProductOnGrocerylist(3, product3, grocerylist2, 1);
        var productOnGrocerylist3 = new ProductOnGrocerylist(4, product2, grocerylist2, 4);

        saveAllProducts(List.of(product, product2, product3));
        saveAllGrocerylists(List.of(grocerylist, grocerylist2));
        saveAllProductsOnGrocerylist(List.of(
                productOnGrocerylist,
                productOnGrocerylist1,
                productOnGrocerylist2,
                productOnGrocerylist3));

        //Act
        deleteProductsOnGrocerylistByGrocerylistId(grocerylist);

        //Assert
        assertEquals(0, findProductsOnGrocerylist(grocerylist).size());
        assertEquals(2, findProductsOnGrocerylist(grocerylist2).size());
        assertEquals(2, findAllGrocerylists().size());
        assertEquals(3, findAllProducts().size());

    }

    private void saveGrocerylist(Grocerylist grocerylist) {
        grocerylistRepo.save(grocerylist);
    }

    private void saveAllGrocerylists(List<Grocerylist> grocerylists) {
        grocerylistRepo.saveAll(grocerylists);
    }

    private void saveProducts(Product product) {
        productRepo.save(product);
    }

    private void saveAllProducts(List<Product> products) {
        productRepo.saveAll(products);
    }

    private void saveAllProductsOnGrocerylist(
            List<ProductOnGrocerylist> productsOnGrocerylist) {
        productOnGrocerylistRepo.saveAll(productsOnGrocerylist);
    }

    private List<ProductOnGrocerylist> findAllProductsOnGrocerylists() {
        return productOnGrocerylistRepo.findAll();
    }

    private List<ProductOnGrocerylist> findProductsOnGrocerylist(Grocerylist grocerylist) {
        return productOnGrocerylistRepo.findByGroceryListId(grocerylist.getId());
    }

    private void deleteProductsOnGrocerylistByGrocerylistId(Grocerylist grocerylist) {
        productOnGrocerylistRepo.deleteByGroceryListId(grocerylist.getId());
    }

    private List<Grocerylist> findAllGrocerylists() {
        return grocerylistRepo.findAll();
    }

    private List<Product> findAllProducts() {
        return productRepo.findAll();
    }
}