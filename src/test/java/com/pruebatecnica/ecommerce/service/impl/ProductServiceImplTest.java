package com.pruebatecnica.ecommerce.service.impl;

import com.pruebatecnica.ecommerce.entity.ProductDocument;
import com.pruebatecnica.ecommerce.exception.ProductNotFoundException;
import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.model.Product;
import com.pruebatecnica.ecommerce.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductMapper productMapper;

    @Mock
    IProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void createNewProduct_ShouldSaveProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setStock(10);

        ProductDocument productDocument = new ProductDocument();
        when(productMapper.toDocument(product)).thenReturn(productDocument);

        productService.createNewProduct(product);

        verify(productRepository, times(1)).save(productDocument);
        assertNotNull(product.getProductId());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ASC", "DESC"})
    void getAllProductsPaged_ShouldReturnPagedProducts(String direction) {
        int page = 0;
        int size = 10;

        ProductDocument productDocument = new ProductDocument();
        Product product = new Product();

        when(productRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(productDocument)));

        when(productMapper.toProduct(productDocument)).thenReturn(product);

        Page<Product> result = productService.getAllProductsPaged(page, size, direction);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
        String productId = UUID.randomUUID().toString();
        ProductDocument productDocument = new ProductDocument();
        Product product = new Product();

        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productDocument));
        when(productMapper.toProduct(productDocument)).thenReturn(product);

        Product result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findByProductId(productId);
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotExists() {
        String productId = UUID.randomUUID().toString();

        when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findByProductId(productId);
    }

    @Test
    void updateProduct_ShouldUpdateExistingProduct() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setName("Updated Name");
        product.setPrice(BigDecimal.valueOf(200.0));
        product.setStock(20);

        ProductDocument productDocument = new ProductDocument();

        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.of(productDocument));
        when(productMapper.toProduct(productDocument)).thenReturn(product);
        when(productMapper.toDocument(any(Product.class))).thenReturn(productDocument);

        productService.updateProduct(product);

        verify(productRepository, times(1)).save(productDocument);
        assertEquals("Updated Name", productDocument.getName());
    }

    @Test
    void deleteProduct_ShouldDeleteExistingProduct() {
        String productId = UUID.randomUUID().toString();
        ProductDocument productDocument = new ProductDocument();
        productDocument.setProductId(productId);

        Product product = new Product();
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productDocument));
        when(productMapper.toProduct(productDocument)).thenReturn(product);
        when(productMapper.toDocument(product)).thenReturn(productDocument);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(productDocument);
    }


    @Test
    void deleteProduct_ShouldThrowException_WhenNotExists() {
        String productId = UUID.randomUUID().toString();

        when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).findByProductId(productId);
    }
}