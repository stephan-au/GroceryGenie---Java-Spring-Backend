package com.yougrocery.yougrocery.grocerylist.controllers;

import com.yougrocery.yougrocery.authentication.config.SecurityConfiguration;
import com.yougrocery.yougrocery.authentication.services.JwtService;
import com.yougrocery.yougrocery.authentication.services.UserService;
import com.yougrocery.yougrocery.grocerylist.models.GroceryItem;
import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import com.yougrocery.yougrocery.grocerylist.models.Product;
import com.yougrocery.yougrocery.grocerylist.services.GroceryItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.yougrocery.yougrocery.assertionhelpers.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroceryItemController.class)
@Import(SecurityConfiguration.class)
class GroceryItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;
    @MockBean
    UserService userService;

    @MockBean
    GroceryItemService groceryItemService;

    @Test
    @WithMockUser
    void addGroceryItem_works() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        var product = new Product("Product name 1");
        var groceryItem = new GroceryItem(product, grocerylist, 1);

        when(groceryItemService.addGroceryItem(product.getName(), grocerylist.getId()))
                .thenReturn(groceryItem);


        //Act
        mockMvc.perform(post("/api/v1/grocery_item/grocerylist/{grocerylist_id}/product/{product_name}",
                        grocerylist.getId(),
                        product.getName())
                        .header("Authorization", "Bearer " + "test-token"))
                .andExpectAll(
                        status().isCreated(),
                        responseBody().containsObjectAsJson(groceryItem, GroceryItem.class));

        //Assert
        verify(groceryItemService)
                .addGroceryItem(product.getName(), grocerylist.getId());
    }

    @Test
    @WithMockUser
    void findGroceryItems_works() throws Exception {
        //Arrange
        int grocerylistId = 1;

        //Act
        mockMvc.perform(get("/api/v1/grocery_item/grocerylist/{grocerylist_id}", grocerylistId))
                .andExpectAll(status().isOk());

        //Assert
        verify(groceryItemService)
                .findByGroceryListId(grocerylistId);
    }

    @Test
    @WithMockUser
    void deleteGroceryItem_works() throws Exception {
        int groceryItemId = 1;

        //Act
        mockMvc.perform(delete("/api/v1/grocery_item/{id}", groceryItemId))
                .andExpectAll(status().isOk());

        //Assert
        verify(groceryItemService)
                .deleteById(groceryItemId);
    }
}