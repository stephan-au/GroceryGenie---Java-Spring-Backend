package com.yougrocery.yougrocery.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.services.GrocerylistService;
import com.yougrocery.yougrocery.services.ProductOnGrocerylistService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.yougrocery.yougrocery.controllers.ResponseBodyMatchers.responseBody;
import static org.assertj.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GrocerylistController.class)
class GrocerylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GrocerylistService grocerylistService;
    @MockBean
    private ProductOnGrocerylistService productOnGrocerylistService;

    @Captor
    ArgumentCaptor<Grocerylist> capturedGrocerylist;

    @Test
    void createGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        when(grocerylistService.save(any())).thenAnswer(returnsFirstArg());

        //Act
        mockMvc.perform(post("/api/grocerylist")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(grocerylist)))
                .andExpectAll(
                        status().isCreated(),
                        responseBody().containsObjectAsJson(grocerylist, Grocerylist.class));

        //Assert
        verify(grocerylistService).save(capturedGrocerylist.capture());
        assertThat(capturedGrocerylist.getValue())
                .hasName("Test name 1");
    }

    @Test
    void getGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        when(grocerylistService.findById(1)).thenReturn(grocerylist);

        //Act
        mockMvc.perform(get("/api/grocerylist/{id}", 1))
                .andExpectAll(
                        status().isOk(),
                        responseBody().containsObjectAsJson(grocerylist, Grocerylist.class));

        //Assert
        verify(grocerylistService).findById(1);
    }

    @Test
    void updateGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        when(grocerylistService.save(any())).thenAnswer(returnsFirstArg());

        //Act
        mockMvc.perform(put("/api/grocerylist")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(grocerylist)))
                .andExpectAll(
                        status().isOk(),
                        responseBody().containsObjectAsJson(grocerylist, Grocerylist.class));

        //Assert
        verify(grocerylistService).save(capturedGrocerylist.capture());
        assertThat(capturedGrocerylist.getValue())
                .hasName("Test name 1");
    }

    @Test
    void deleteGrocerylistWorks() throws Exception {
        //Act + Assert
        int grocerylistId = 1;
        mockMvc.perform(delete("/api/grocerylist/{id}", grocerylistId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(grocerylistService).delete(grocerylistId);

    }
}