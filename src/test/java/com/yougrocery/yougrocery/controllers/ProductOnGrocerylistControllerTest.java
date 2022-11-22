package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import com.yougrocery.yougrocery.services.ProductOnGrocerylistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.yougrocery.yougrocery.controllers.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductOnGrocerylistController.class)
class ProductOnGrocerylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductOnGrocerylistService productOnGrocerylistService;


    @Test
    void createGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        var product = new Product("Product name 1");
        var productOnGrocerylist = new ProductOnGrocerylist(product, grocerylist, 1);

        when(productOnGrocerylistService.addProductOnGrocerylist(product.getName(), grocerylist.getId()))
                .thenReturn(productOnGrocerylist);

        //Act
        mockMvc.perform(post(
                        "/api/product_on_grocerylist/grocerylist/{grocerylist_id}/product/{product_name}",
                        grocerylist.getId(),
                        product.getName()))
                .andExpectAll(
                        status().isCreated(),
                        responseBody().containsObjectAsJson(productOnGrocerylist, ProductOnGrocerylist.class));

        //Assert
        verify(productOnGrocerylistService)
                .addProductOnGrocerylist(product.getName(), grocerylist.getId());
    }
}