package com.pruebatecnica.ecommerce.service.impl;

import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.model.Product;
import com.pruebatecnica.ecommerce.repository.ProductRepository;
import com.pruebatecnica.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createNewProduct(Product product) {
        productRepository.save(productMapper.toDocument(product));
    }
}
