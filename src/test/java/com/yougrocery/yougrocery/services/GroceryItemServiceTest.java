package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.GroceryItem;
import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.repositories.GroceryItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import javax.persistence.EntityNotFoundException;

import static org.assertj.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroceryItemServiceTest {

    @Mock
    private GroceryItemRepository groceryItemRepo;
    @Mock
    private ProductService productService;
    @Mock
    private GrocerylistService grocerylistService;

    @InjectMocks
    private GroceryItemService groceryItemService;

    @Captor
    private ArgumentCaptor<GroceryItem> groceryItemCaptor;

    @Test
    void addGroceryItem_works() {
        //Arrange
        String expectedProductName = "Product name 1";
        var expectedProduct = new Product(expectedProductName);
        var expectedGrocerylist = new Grocerylist("Grocerylist 1");

        when(getOrCreateProduct(expectedProductName)).thenReturn(expectedProduct);
        when(getGrocerylistById(anyInt())).thenReturn(expectedGrocerylist);
        when(saveGroceryItem(any(GroceryItem.class))).thenAnswer(returnsFirstArg());

        //Act
        var actualGroceryItem = addGroceryItem(expectedProductName, 1);

        //Assert
        verify(productService).findOrCreateProduct(expectedProductName);
        verify(grocerylistService).findById(1);
        verify(groceryItemRepo).save(groceryItemCaptor.capture());

        assertThat(groceryItemCaptor.getValue())
                .hasProduct(expectedProduct)
                .hasGroceryList(expectedGrocerylist)
                .hasAmount(1);

        assertThat(actualGroceryItem)
                .hasProduct(expectedProduct)
                .hasGroceryList(expectedGrocerylist)
                .hasAmount(1);
    }

    @Test
    void addProductOnNonExistingGrocerylist_throwsErrorAndNoGroceryItemAdded() {
        //Arrange
        String expectedProductName = "Product name 1";
        var expectedProduct = new Product(expectedProductName);

        when(getOrCreateProduct(expectedProductName)).thenReturn(expectedProduct);
        when(getGrocerylistById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        assertThrows(EntityNotFoundException.class,
                () -> addGroceryItem(expectedProductName, 1));

        //Assert
        verify(productService).findOrCreateProduct(expectedProductName);
        verify(grocerylistService).findById(1);
        verify(groceryItemRepo, never()).save(any());
    }

    @Test
    void addTheSameGroceryItem_throwsError() {
        //Arrange
        String expectedProductName = "Product name 1";
        var expectedProduct = new Product(expectedProductName);
        var expectedGrocerylist = new Grocerylist("Grocerylist 1");

        when(getOrCreateProduct(expectedProductName)).thenReturn(expectedProduct);
        when(getGrocerylistById(anyInt())).thenReturn(expectedGrocerylist);
        when(saveGroceryItem(any(GroceryItem.class))).thenAnswer(returnsFirstArg()).thenThrow(DuplicateKeyException.class);

        //Act
        addGroceryItem(expectedProductName, 1);
        assertThrows(
                DuplicateKeyException.class,
                () -> addGroceryItem(expectedProductName, 1));

        //Assert
        verify(productService, times(2)).findOrCreateProduct(expectedProductName);
        verify(grocerylistService, times(2)).findById(1);
        verify(groceryItemRepo, times(2)).save(any());
    }

    @Test
    void getGroceryItemsByGrocerylistId_Works() {
        groceryItemService.findByGroceryListId(1);

        verify(groceryItemRepo).findByGroceryListId(1);
    }

    @Test
    void deleteGroceryItem_Works() {
        groceryItemService.deleteById(1);

        verify(groceryItemRepo).deleteById(1);
    }

    private Grocerylist getGrocerylistById(int id) {
        return grocerylistService.findById(id);
    }

    private Product getOrCreateProduct(String expectedProductName) {
        return productService.findOrCreateProduct(expectedProductName);
    }

    private GroceryItem saveGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepo.save(groceryItem);
    }


    private GroceryItem addGroceryItem(String productName, int grocerylistId) {
        return groceryItemService.addGroceryItem(productName, grocerylistId);
    }
}