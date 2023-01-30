package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.GroceryItem;
import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.services.GroceryItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.yougrocery.yougrocery.controllers.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroceryItemController.class)
class GroceryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroceryItemService groceryItemService;


    @Test
    void addGroceryItem_works() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        var product = new Product("Product name 1");
        var groceryItem = new GroceryItem(product, grocerylist, 1);

        when(groceryItemService.addGroceryItem(product.getName(), grocerylist.getId()))
                .thenReturn(groceryItem);

        //Act
        mockMvc.perform(post(
                        "/api/product_on_grocerylist/grocerylist/{grocerylist_id}/product/{product_name}",
                        grocerylist.getId(),
                        product.getName()))
                .andExpectAll(
                        status().isCreated(),
                        responseBody().containsObjectAsJson(groceryItem, GroceryItem.class));

        //Assert
        verify(groceryItemService)
                .addGroceryItem(product.getName(), grocerylist.getId());
    }

    @Test
    void findGroceryItems_works() throws Exception {
        //Arrange
        int grocerylistId = 1;

        //Act
        mockMvc.perform(get(
                        "/api/product_on_grocerylist/grocerylist/{grocerylist_id}",
                        grocerylistId))
                .andExpectAll(status().isOk());

        //Assert
        verify(groceryItemService)
                .findByGroceryListId(grocerylistId);
    }

    @Test
    void deleteGroceryItem_works() throws Exception {
        int groceryItemId = 1;

        //Act
        mockMvc.perform(delete(
                        "/api/product_on_grocerylist/{id}",
                        groceryItemId))
                .andExpectAll(status().isOk());

        //Assert
        verify(groceryItemService)
                .deleteById(groceryItemId);
    }
}