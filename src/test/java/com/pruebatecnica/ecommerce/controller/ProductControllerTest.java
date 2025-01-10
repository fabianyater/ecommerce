package com.pruebatecnica.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebatecnica.ecommerce.dto.request.ProductRequest;
import com.pruebatecnica.ecommerce.dto.response.ProductResponse;
import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.model.Product;
import com.pruebatecnica.ecommerce.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(100.0));
        request.setStock(10);

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());

        when(productMapper.toProduct(any(ProductRequest.class))).thenReturn(product);
        doNothing().when(productService).createNewProduct(any(Product.class));

        mockMvc.perform(post("/api/v1/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(productService).createNewProduct(any(Product.class));
    }

    @Test
    void testGetAllProductsPaged() throws Exception {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(50.0));
        product.setStock(5);

        Page<Product> productPage = new PageImpl<>(List.of(product));

        when(productService.getAllProductsPaged(anyInt(), anyInt(), anyString()))
                .thenReturn(productPage);

        mockMvc.perform(
                        get("/api/v1/products/")
                                .param("page", "0")
                                .param("size", "10")
                                .param("direction", "asc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productId").value(product.getProductId()))
                .andExpect(jsonPath("$.content[0].name").value(product.getName()))
                .andExpect(jsonPath("$.content[0].price").value(product.getPrice().doubleValue()))
                .andExpect(jsonPath("$.content[0].stock").value(product.getStock()));

        verify(productService).getAllProductsPaged(0, 10, "asc");
    }

    @Test
    void testGetProductById() throws Exception {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);
        product.setName("Test Product");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(productId);
        productResponse.setName("Test Product");

        when(productService.getProductById(productId)).thenReturn(product);
        when(productMapper.toProductResponse(product)).thenReturn(productResponse);

        mockMvc.perform(get("/api/v1/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).getProductById(productId);
        verify(productMapper, times(1)).toProductResponse(product);
    }

    @Test
    void updateProduct_shouldReturnNoContent() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Updated Product");
        productRequest.setDescription("Updated Description");
        productRequest.setPrice(BigDecimal.valueOf(100.0));
        productRequest.setStock(10);

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        when(productMapper.toProduct(productRequest)).thenReturn(product);

        mockMvc.perform(put("/api/v1/products/" + product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isNoContent());

        verify(productMapper).toProduct(productRequest);
        verify(productService).updateProduct(product);
    }

    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {
        String productId = UUID.randomUUID().toString();
        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/products/" + productId))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(productId);
    }


}