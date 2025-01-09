package com.pruebatecnica.ecommerce.controller;

import com.pruebatecnica.ecommerce.dto.request.ProductRequest;
import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService IProductService;
    private final ProductMapper productMapper;

    @PostMapping("/")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);

        IProductService.createNewProduct(product);
        URI location = URI.create("/api/v1/products/" + product.getId());

        return ResponseEntity.created(location).build();
    }

}
