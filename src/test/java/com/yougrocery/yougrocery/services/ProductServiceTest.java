package com.yougrocery.yougrocery.services;

import com.yougrocery.yougrocery.models.Product;
import com.yougrocery.yougrocery.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Test
    void findOrCreatesExistingProduct_returnsExistingProduct() {
        String expectedName = "product name 1";
        when(findProductByName(expectedName)).thenReturn(Optional.of(new Product(expectedName)));

        //Act
        Product actualProduct = productService.findOrCreateProduct(expectedName);

        verify(productRepository).findByName(expectedName);
        verify(productRepository, never()).save(any());
        assertThat(actualProduct)
                .hasName(expectedName);
    }

    @Test
    void findOrCreateNonExistingProduct_createsNewProduct() {
        String expectedName = "product name 1";
        when(findProductByName(expectedName)).thenReturn(Optional.empty());
        when(saveProduct(any(Product.class))).thenAnswer(returnsFirstArg());

        //Act
        Product actualProduct = productService.findOrCreateProduct(expectedName);

        verify(productRepository).findByName(expectedName);
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue())
                .hasName(expectedName);
        assertThat(actualProduct)
                .hasName(expectedName);
    }

    private Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}