package com.pruebatecnica.ecommerce.mapper;

import com.pruebatecnica.ecommerce.dto.request.ProductRequest;
import com.pruebatecnica.ecommerce.dto.response.ProductResponse;
import com.pruebatecnica.ecommerce.entity.ProductDocument;
import com.pruebatecnica.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductDocument toDocument(Product product);
    Product toProduct(ProductRequest productRequest);
    Product toProduct(ProductDocument productDocument);
    ProductResponse toProductResponse(Product product);
}
