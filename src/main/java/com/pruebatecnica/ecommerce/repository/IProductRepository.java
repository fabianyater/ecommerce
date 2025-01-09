package com.pruebatecnica.ecommerce.repository;

import com.pruebatecnica.ecommerce.entity.ProductDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IProductRepository extends MongoRepository<ProductDocument, ObjectId> {
    Optional<ProductDocument> findByProductId(String productId);
}
