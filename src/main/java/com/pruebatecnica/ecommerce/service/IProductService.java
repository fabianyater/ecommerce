package com.pruebatecnica.ecommerce.service;

import com.pruebatecnica.ecommerce.model.Product;
import org.springframework.data.domain.Page;

public interface IProductService {
    void createNewProduct(Product product);
    Page<Product> getAllProductsPaged(int page, int size, String direction);
}
