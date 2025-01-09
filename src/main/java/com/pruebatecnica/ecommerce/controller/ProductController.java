package com.pruebatecnica.ecommerce.controller;

import com.pruebatecnica.ecommerce.dto.request.ProductRequest;
import com.pruebatecnica.ecommerce.dto.response.ProductListResponse;
import com.pruebatecnica.ecommerce.dto.response.ProductResponse;
import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.model.Product;
import com.pruebatecnica.ecommerce.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);

        productService.createNewProduct(product);
        URI location = URI.create("/api/v1/products/" + product.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProductListResponse>> getAllProductsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String direction) {
        Page<Product> products = productService.getAllProductsPaged(page, size, direction);
        Page<ProductListResponse> productListResponses = products.map(product -> {
            var productListResponse = new ProductListResponse();
            productListResponse.setProductId(product.getProductId());
            productListResponse.setName(product.getName());
            productListResponse.setPrice(product.getPrice());
            productListResponse.setStock(product.getStock());
            return productListResponse;
        });

        return ResponseEntity.ok(productListResponses);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId) {
        var product = productService.getProductById(productId);
        ProductResponse productResponse = productMapper.toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable String productId, @Valid @RequestBody ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        product.setProductId(productId);

        productService.updateProduct(product);

        return ResponseEntity.noContent().build();
    }

}
