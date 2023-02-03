package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.GroceryItem;
import com.yougrocery.yougrocery.repositories.GroceryItemRepository;
import com.yougrocery.yougrocery.services.GrocerylistService;
import com.yougrocery.yougrocery.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddGroceryItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;
    @Autowired
    private GrocerylistService grocerylistService;
    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Test
    void addExistingProductOnExistingGrocerylist_addGroceryItem() throws Exception {
        String expectedProductName = "Product Name 123";
        Product expectedProduct = saveProduct(expectedProductName);
        Grocerylist expectedGrocerylist = saveGrocerylist("Grocerylist 1");

        //Act
        mockMvc.perform(post("/api/product_on_grocerylist/grocerylist/{id}/product/{product_name}",
                        expectedGrocerylist.getId(),
                        expectedProductName))
                .andExpectAll(status().isCreated());

        //Assert
        List<GroceryItem> groceryItems = findGroceryItems(expectedGrocerylist);
        assertEquals(groceryItems.size(), 1);
        assertEquals(expectedProduct, groceryItems.get(0).getProduct());
        assertEquals(1, groceryItems.get(0).getAmount());
    }

    private Product saveProduct(String expectedProductName) {
        return productService.save(new Product(expectedProductName));
    }

    private Grocerylist saveGrocerylist(String name) {
        return grocerylistService.save(new Grocerylist(name));
    }

    private List<GroceryItem> findGroceryItems(Grocerylist grocerylist) {
        return groceryItemRepository.findByGroceryListId(grocerylist.getId());
    }

}
