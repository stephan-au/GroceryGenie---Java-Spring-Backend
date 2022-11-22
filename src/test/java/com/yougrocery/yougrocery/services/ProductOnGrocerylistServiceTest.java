package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Grocerylist;
import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.models.ProductOnGrocerylist;
import com.yougrocery.yougrocery.repositories.ProductOnGrocerylistRepository;
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
class ProductOnGrocerylistServiceTest {

    @Mock
    private ProductOnGrocerylistRepository productOnGrocerylistRepo;
    @Mock
    private ProductService productService;
    @Mock
    private GrocerylistService grocerylistService;

    @InjectMocks
    private ProductOnGrocerylistService productOnGrocerylistService;

    @Captor
    private ArgumentCaptor<ProductOnGrocerylist> productOnGrocerylistCaptor;

    @Test
    void addProductOnGrocerylist_works() {
        //Arrange
        String expectedProductName = "Product name 1";
        var expectedProduct = new Product(expectedProductName);
        var expectedGrocerylist = new Grocerylist("Grocerylist 1");

        when(getOrCreateProduct(expectedProductName)).thenReturn(expectedProduct);
        when(getGrocerylistById(anyInt())).thenReturn(expectedGrocerylist);
        when(saveProductOnGrocerylist(any(ProductOnGrocerylist.class))).thenAnswer(returnsFirstArg());

        //Act
        var actualProductOnGrocerylist = addProductOnGrocerylist(expectedProductName, 1);

        //Assert
        verify(productService).findOrCreateProduct(expectedProductName);
        verify(grocerylistService).findById(1);
        verify(productOnGrocerylistRepo).save(productOnGrocerylistCaptor.capture());

        assertThat(productOnGrocerylistCaptor.getValue())
                .hasProduct(expectedProduct)
                .hasGroceryList(expectedGrocerylist)
                .hasAmount(1);

        assertThat(actualProductOnGrocerylist)
                .hasProduct(expectedProduct)
                .hasGroceryList(expectedGrocerylist)
                .hasAmount(1);
    }

    @Test
    void addProductOnNonExistingGrocerylist_throwsErrorAndNoProductOnGroceryListAdded() {
        //Arrange
        String expectedProductName = "Product name 1";
        var expectedProduct = new Product(expectedProductName);

        when(getOrCreateProduct(expectedProductName)).thenReturn(expectedProduct);
        when(getGrocerylistById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act
        assertThrows(EntityNotFoundException.class,
                () -> addProductOnGrocerylist(expectedProductName, 1));

        //Assert
        verify(productService).findOrCreateProduct(expectedProductName);
        verify(grocerylistService).findById(1);
        verify(productOnGrocerylistRepo, never()).save(any());
    }

    @Test
    void addTheSameProductOnGrocerylist_throwsError() {
        //Arrange
        String expectedProductName = "Product name 1";
        var expectedProduct = new Product(expectedProductName);
        var expectedGrocerylist = new Grocerylist("Grocerylist 1");

        when(getOrCreateProduct(expectedProductName)).thenReturn(expectedProduct);
        when(getGrocerylistById(anyInt())).thenReturn(expectedGrocerylist);
        when(saveProductOnGrocerylist(any(ProductOnGrocerylist.class))).thenAnswer(returnsFirstArg()).thenThrow(DuplicateKeyException.class);

        //Act
        addProductOnGrocerylist(expectedProductName, 1);
        assertThrows(
                DuplicateKeyException.class,
                () -> addProductOnGrocerylist(expectedProductName, 1));

        //Assert
        verify(productService, times(2)).findOrCreateProduct(expectedProductName);
        verify(grocerylistService, times(2)).findById(1);
        verify(productOnGrocerylistRepo, times(2)).save(any());
    }

    @Test
    void getProductsOnGrocerylistByGrocerylistId_Works() {
        productOnGrocerylistService.findByGroceryListId(1);

        verify(productOnGrocerylistRepo).findByGroceryListId(1);
    }

    @Test
    void deleteProductOnGrocerylist_Works() {
        productOnGrocerylistService.deleteById(1);

        verify(productOnGrocerylistRepo).deleteById(1);
    }

    private Grocerylist getGrocerylistById(int id) {
        return grocerylistService.findById(id);
    }

    private Product getOrCreateProduct(String expectedProductName) {
        return productService.findOrCreateProduct(expectedProductName);
    }

    private ProductOnGrocerylist saveProductOnGrocerylist(ProductOnGrocerylist productOnGrocerylist) {
        return productOnGrocerylistRepo.save(productOnGrocerylist);
    }


    private ProductOnGrocerylist addProductOnGrocerylist(String productName, int grocerylistId) {
        return productOnGrocerylistService.addProductOnGrocerylist(productName, grocerylistId);
    }
}