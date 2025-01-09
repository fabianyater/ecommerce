package com.pruebatecnica.ecommerce.service.impl;

import com.pruebatecnica.ecommerce.entity.ProductDocument;
import com.pruebatecnica.ecommerce.exception.ProductNotFoundException;
import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.model.Product;
import com.pruebatecnica.ecommerce.repository.IProductRepository;
import com.pruebatecnica.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createNewProduct(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(productMapper.toDocument(product));
    }

    @Override
    public Page<Product> getAllProductsPaged(int page, int size, String direction) {
        Sort sort = Sort.by(direction.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Pageable pagination = PageRequest.of(page, size, sort);
        Page<ProductDocument> productDocumentsPage = productRepository.findAll(pagination);

        return productDocumentsPage.map(productMapper::toProduct);
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findByProductId(productId)
                .map(productMapper::toProduct)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }
}
