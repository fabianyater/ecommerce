package com.pruebatecnica.ecommerce.repository;

import com.pruebatecnica.ecommerce.entity.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDocument, Long> {
}
