package com.yougrocery.yougrocery.grocerylist.integration;

import com.yougrocery.yougrocery.grocerylist.models.GroceryItem;
import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import com.yougrocery.yougrocery.grocerylist.models.Product;
import com.yougrocery.yougrocery.grocerylist.repositories.GroceryItemRepository;
import com.yougrocery.yougrocery.grocerylist.services.GrocerylistService;
import com.yougrocery.yougrocery.grocerylist.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddGroceryItemIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductService productService;
    @Autowired
    GrocerylistService grocerylistService;
    @Autowired
    GroceryItemRepository groceryItemRepository;

    @Test
    @WithMockUser
    void addExistingProductOnExistingGrocerylist_addGroceryItem() throws Exception {
        String expectedProductName = "Product Name 123";
        Product expectedProduct = saveProduct(expectedProductName);
        Grocerylist expectedGrocerylist = saveGrocerylist("Grocerylist 1");

        //Act
        mockMvc.perform(post("/api/v1/grocery_item/grocerylist/{id}/product/{product_name}",
                        expectedGrocerylist.getId(),
                        expectedProductName))
                .andExpectAll(status().isCreated());

        //Assert
        List<GroceryItem> groceryItems = findGroceryItems(expectedGrocerylist);
        assertEquals(groceryItems.size(), 1);
        assertEquals(expectedProduct, groceryItems.get(0).getProduct());
        assertEquals(1, groceryItems.get(0).getAmount());
    }

    Product saveProduct(String expectedProductName) {
        return productService.save(new Product(expectedProductName));
    }

    Grocerylist saveGrocerylist(String name) {
        return grocerylistService.save(new Grocerylist(name));
    }

    List<GroceryItem> findGroceryItems(Grocerylist grocerylist) {
        return groceryItemRepository.findByGroceryListId(grocerylist.getId());
    }

}
