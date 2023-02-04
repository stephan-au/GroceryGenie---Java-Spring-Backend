package com.yougrocery.yougrocery.grocerylist.repositories;

import com.yougrocery.yougrocery.grocerylist.models.GroceryItem;
import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import com.yougrocery.yougrocery.grocerylist.models.Product;
import com.yougrocery.yougrocery.grocerylist.repositories.GroceryItemRepository;
import com.yougrocery.yougrocery.grocerylist.repositories.GrocerylistRepository;
import com.yougrocery.yougrocery.grocerylist.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class GroceryItemRepositoryTest {

    @Autowired
    GroceryItemRepository groceryItemRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    GrocerylistRepository grocerylistRepo;

    @Test
    void savingTheSameProductOnSameGrocerylist_throwsUniqueConstraint() {
        //Arrange
        var product = new Product("Product name 1");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var groceryItem = new GroceryItem(1, product, grocerylist, 5);
        var groceryItem2 = new GroceryItem(2, product, grocerylist, 1);

        //act
        saveProducts(product);
        saveGrocerylist(grocerylist);

        //Assert
        groceryItemRepo.save(groceryItem);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> groceryItemRepo.saveAndFlush(groceryItem2));
    }

    @Test
    void twoGroceryItems_findGroceryItemsByGrocerylistId_returnsTwoGroceryItems() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var groceryItem = new GroceryItem(1, product, grocerylist, 1);
        var groceryItem2 = new GroceryItem(2, product2, grocerylist, 1);

        saveAllProducts(List.of(product, product2));
        saveGrocerylist(grocerylist);
        saveAllGroceryItems(List.of(groceryItem, groceryItem2));

        //Act
        List<GroceryItem> actualGroceryItems = findGroceryItems(grocerylist);

        //Assert
        assertEquals(2, actualGroceryItems.size());
        assertEquals(product, actualGroceryItems.get(0).getProduct());
        assertEquals(grocerylist, actualGroceryItems.get(0).getGroceryList());
        assertEquals(1, actualGroceryItems.get(0).getAmount());

        assertEquals(product2, actualGroceryItems.get(1).getProduct());
        assertEquals(grocerylist, actualGroceryItems.get(1).getGroceryList());
        assertEquals(1, actualGroceryItems.get(1).getAmount());
    }

    @Test
    void multipleProductsOnMultipleGrocerylist_findProductsOnEachGrocerylistByGrocerylistId_returnsCorrectGroceryItems() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var product3 = new Product("Product name 3");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var grocerylist2 = new Grocerylist("Grocerylist 2");
        var groceryItem = new GroceryItem(1, product, grocerylist, 1);
        var groceryItem1 = new GroceryItem(2, product2, grocerylist, 2);
        var groceryItem2 = new GroceryItem(3, product3, grocerylist2, 1);
        var groceryItem3 = new GroceryItem(4, product2, grocerylist2, 4);

        saveAllProducts(List.of(product, product2, product3));
        saveAllGrocerylists(List.of(grocerylist, grocerylist2));
        saveAllGroceryItems(List.of(
                groceryItem,
                groceryItem1,
                groceryItem2,
                groceryItem3));

        //Act
        List<GroceryItem> actualGroceryItems = findGroceryItems(grocerylist);
        List<GroceryItem> actualGroceryItems2 = findGroceryItems(grocerylist2);

        //Assert
        assertEquals(2, actualGroceryItems.size());
        assertEquals(product, actualGroceryItems.get(0).getProduct());
        assertEquals(grocerylist, actualGroceryItems.get(0).getGroceryList());
        assertEquals(1, actualGroceryItems.get(0).getAmount());

        assertEquals(product2, actualGroceryItems.get(1).getProduct());
        assertEquals(grocerylist, actualGroceryItems.get(1).getGroceryList());
        assertEquals(2, actualGroceryItems.get(1).getAmount());

        assertEquals(2, actualGroceryItems2.size());
        assertEquals(product3, actualGroceryItems2.get(0).getProduct());
        assertEquals(grocerylist2, actualGroceryItems2.get(0).getGroceryList());
        assertEquals(1, actualGroceryItems2.get(0).getAmount());

        assertEquals(product2, actualGroceryItems2.get(1).getProduct());
        assertEquals(grocerylist2, actualGroceryItems2.get(1).getGroceryList());
        assertEquals(4, actualGroceryItems2.get(1).getAmount());
    }

    @Test
    void twoGroceryItems_deleteGroceryItemsByGrocerylistId_deletesGroceryItems() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var groceryItem = new GroceryItem(1, product, grocerylist, 1);
        var groceryItem2 = new GroceryItem(2, product2, grocerylist, 1);

        saveAllProducts(List.of(product, product2));
        saveGrocerylist(grocerylist);
        saveAllGroceryItems(List.of(groceryItem, groceryItem2));

        //Act
        deleteGroceryItemsByGrocerylistId(grocerylist);

        //Assert
        assertEquals(0, findAllGroceryItemss().size());
    }

    @Test
    void multipleProductsOnMultipleGrocerylist_deleteProductsFromGrocerylist1_onlyDeletesProductsFromGrocerylist1() {
        //Arrange
        var product = new Product("Product name 1");
        var product2 = new Product("Product name 2");
        var product3 = new Product("Product name 3");
        var grocerylist = new Grocerylist("Grocerylist 1");
        var grocerylist2 = new Grocerylist("Grocerylist 2");
        var groceryItem = new GroceryItem(1, product, grocerylist, 1);
        var groceryItem1 = new GroceryItem(2, product2, grocerylist, 2);
        var groceryItem2 = new GroceryItem(3, product3, grocerylist2, 1);
        var groceryItem3 = new GroceryItem(4, product2, grocerylist2, 4);

        saveAllProducts(List.of(product, product2, product3));
        saveAllGrocerylists(List.of(grocerylist, grocerylist2));
        saveAllGroceryItems(List.of(
                groceryItem,
                groceryItem1,
                groceryItem2,
                groceryItem3));

        //Act
        deleteGroceryItemsByGrocerylistId(grocerylist);

        //Assert
        assertEquals(0, findGroceryItems(grocerylist).size());
        assertEquals(2, findGroceryItems(grocerylist2).size());
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

    private void saveAllGroceryItems(
            List<GroceryItem> groceryItems) {
        groceryItemRepo.saveAll(groceryItems);
    }

    private List<GroceryItem> findAllGroceryItemss() {
        return groceryItemRepo.findAll();
    }

    private List<GroceryItem> findGroceryItems(Grocerylist grocerylist) {
        return groceryItemRepo.findByGroceryListId(grocerylist.getId());
    }

    private void deleteGroceryItemsByGrocerylistId(Grocerylist grocerylist) {
        groceryItemRepo.deleteByGroceryListId(grocerylist.getId());
    }

    private List<Grocerylist> findAllGrocerylists() {
        return grocerylistRepo.findAll();
    }

    private List<Product> findAllProducts() {
        return productRepo.findAll();
    }
}