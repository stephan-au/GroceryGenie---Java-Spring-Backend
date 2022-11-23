package com.yougrocery.yougrocery.controllers;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import com.yougrocery.yougrocery.repositories.ProductOnGrocerylistRepository;
import com.yougrocery.yougrocery.services.GrocerylistService;
import com.yougrocery.yougrocery.services.ProductOnGrocerylistService;
import com.yougrocery.yougrocery.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddProductOnGrocerylistIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;
    @Autowired
    private GrocerylistService grocerylistService;
    @Autowired
    private ProductOnGrocerylistRepository productOnGrocerylistRepository;

    @Test
    void addExistingProductOnExistingGrocerylist_addProductOnGrocerylist() throws Exception {
        String expectedProductName = "Product Name 123";
        Product expectedProduct = saveProduct(expectedProductName);
        Grocerylist expectedGrocerylist = saveGrocerylist("Grocerylist 1");

        //Act
        mockMvc.perform(post("/api/product_on_grocerylist/grocerylist/{id}/product/{product_name}",
                        expectedGrocerylist.getId(),
                        expectedProductName))
                .andExpectAll(status().isCreated());

        //Assert
        List<ProductOnGrocerylist> productsOnGrocerylist = findProductsOnGrocerylist(expectedGrocerylist);
        assertEquals(productsOnGrocerylist.size(), 1);
        assertThat(productsOnGrocerylist.get(0))
                .hasProduct(expectedProduct)
                .hasAmount(1);
    }

    private Product saveProduct(String expectedProductName) {
        return productService.save(new Product(expectedProductName));
    }

    private Grocerylist saveGrocerylist(String name) {
        return grocerylistService.save(new Grocerylist(name));
    }

    private List<ProductOnGrocerylist> findProductsOnGrocerylist(Grocerylist grocerylist) {
        return productOnGrocerylistRepository.findByGroceryListId(grocerylist.getId());
    }

}
