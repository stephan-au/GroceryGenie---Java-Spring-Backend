package com.yougrocery.yougrocery.grocerylist.services;

import com.yougrocery.yougrocery.grocerylist.models.Grocerylist;
import com.yougrocery.yougrocery.grocerylist.repositories.GroceryItemRepository;
import com.yougrocery.yougrocery.grocerylist.repositories.GrocerylistRepository;
import com.yougrocery.yougrocery.grocerylist.services.GrocerylistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GrocerylistServiceTest {

    @Mock
    GrocerylistRepository grocerylistRepository;
    @Mock
    GroceryItemRepository groceryItemRepo;
    @InjectMocks
    GrocerylistService grocerylistService;

    @Test
    void saveGrocerylistWorks() {
        //Arrange
        Grocerylist expected = new Grocerylist("Test 12345");
        when(grocerylistRepository.save(any(Grocerylist.class))).thenAnswer(returnsFirstArg());

        //Act
        Grocerylist actual = grocerylistService.save(expected);

        //Assert
        verify(grocerylistRepository).save(expected);
        assertEquals("Test 12345", actual.getName());
    }

    @Test
    void findByIdWorks() {
        //Arrange
        Grocerylist expected = new Grocerylist("Test 12345");
        when(grocerylistRepository.findById(1)).thenReturn(Optional.of(expected));

        //Act
        Grocerylist actual = grocerylistService.findById(1);

        //Assert
        verify(grocerylistRepository).findById(1);
        assertEquals("Test 12345", actual.getName());
    }

    @Test
    void findByIdOnNonExsistingGrocerylist_throwsNoSuchElementException() {
        //Arrange
        Grocerylist expected = new Grocerylist("Test 12345");
        when(grocerylistRepository.findById(1)).thenReturn(Optional.empty());

        //Act
        assertThrows(
                NoSuchElementException.class,
                () -> grocerylistService.findById(1));
    }

    @Test
    void deleteGrocerylistWorks() {
        int grocerylistId = 1;

        //Act
        grocerylistService.delete(grocerylistId);

        //Arrange
        verify(grocerylistRepository).deleteById(grocerylistId);
        verify(groceryItemRepo).deleteByGroceryListId(grocerylistId);
    }
}