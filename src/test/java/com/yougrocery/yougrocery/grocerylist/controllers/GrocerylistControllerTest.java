package com.yougrocery.yougrocery.grocerylist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yougrocery.yougrocery.authentication.config.SecurityConfiguration;
import com.yougrocery.yougrocery.authentication.services.JwtService;
import com.yougrocery.yougrocery.authentication.services.UserService;
import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import com.yougrocery.yougrocery.grocerylist.services.GroceryItemService;
import com.yougrocery.yougrocery.grocerylist.services.GrocerylistService;
import com.yougrocery.yougrocery.assertionhelpers.ResponseBodyMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.yougrocery.yougrocery.assertionhelpers.ResponseBodyMatchers.responseBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GrocerylistController.class)
@Import(SecurityConfiguration.class)
class GrocerylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;

    @MockBean
    private GrocerylistService grocerylistService;
    @MockBean
    private GroceryItemService groceryItemService;

    @Captor
    ArgumentCaptor<Grocerylist> capturedGrocerylist;

    @Test
    void rootWhenUnauthenticatedThen403() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getGrocerylistWhenUnauthenticatedThen403() throws Exception {
        mockMvc.perform(get("/api/v1/grocerylist/{id}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void createGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        when(grocerylistService.save(any())).thenAnswer(returnsFirstArg());

        //Act
        mockMvc.perform(post("/api/v1/grocerylist")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(grocerylist)))
                .andExpectAll(
                        status().isCreated(),
                        responseBody().containsObjectAsJson(grocerylist, Grocerylist.class));

        //Assert
        verify(grocerylistService).save(capturedGrocerylist.capture());
        assertEquals("Test name 1", capturedGrocerylist.getValue().getName());
    }

    @Test
    @WithMockUser
    void getGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        when(grocerylistService.findById(1)).thenReturn(grocerylist);

        //Act
        mockMvc.perform(get("/api/v1/grocerylist/{id}", 1)
                        .header("Authorization", "Bearer " + "test-token"))
                .andExpectAll(
                        status().isOk(),
                        responseBody().containsObjectAsJson(grocerylist, Grocerylist.class));


        //Assert
        verify(grocerylistService).findById(1);
    }

    @Test
    @WithMockUser
    void updateGrocerylistWorks() throws Exception {
        //Arrange
        var grocerylist = new Grocerylist("Test name 1");
        when(grocerylistService.save(any())).thenAnswer(returnsFirstArg());

        //Act
        mockMvc.perform(put("/api/v1/grocerylist")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(grocerylist)))
                .andExpectAll(
                        status().isOk(),
                        responseBody().containsObjectAsJson(grocerylist, Grocerylist.class));

        //Assert
        verify(grocerylistService).save(capturedGrocerylist.capture());
        assertEquals("Test name 1", capturedGrocerylist.getValue().getName());
    }

    @Test
    @WithMockUser
    void deleteGrocerylistWorks() throws Exception {
        //Act + Assert
        int grocerylistId = 1;
        mockMvc.perform(delete("/api/v1/grocerylist/{id}", grocerylistId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(grocerylistService).delete(grocerylistId);

    }
}