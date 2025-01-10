package com.pruebatecnica.ecommerce.controller;

import com.pruebatecnica.ecommerce.dto.request.ProductRequest;
import com.pruebatecnica.ecommerce.dto.response.PagedResponse;
import com.pruebatecnica.ecommerce.dto.response.ProductListResponse;
import com.pruebatecnica.ecommerce.dto.response.ProductResponse;
import com.pruebatecnica.ecommerce.mapper.ProductMapper;
import com.pruebatecnica.ecommerce.model.Product;
import com.pruebatecnica.ecommerce.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final IProductService productService;
    private final ProductMapper productMapper;

    @Operation(
            summary = "Create a new product",
            description = "This method allows the creation of a new product in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                            @ExampleObject(name = "nameLength", value = "Name must be between 3 and 50 characters"),
                            @ExampleObject(name = "priceValue", value = "Price must be greater than 0"),
                            @ExampleObject(name = "stockValue", value = "Stock must be greater than or equal to 0")
                    }
            )),

    })
    @PostMapping("/")
    public ResponseEntity<Void> createProduct(
            @Parameter(description = "Product data", required = true)
            @Valid @RequestBody ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);

        productService.createNewProduct(product);
        URI location = URI.create("/api/v1/products/" + product.getId());

        return ResponseEntity.created(location).build();
    }


    @Operation(
            summary = "Get all products",
            description = "This method returns a list of all products in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PagedResponse.class)
            ))
    })
    @GetMapping("/")
    public ResponseEntity<PagedResponse<ProductListResponse>> getAllProductsPaged(
            @Parameter(description = "Page number", required = true)
            @RequestParam int page,
            @Parameter(description = "Page size", required = true)
            @RequestParam int size,
            @Parameter(description = "Sort direction", required = true)
            @RequestParam String direction) {
        Page<Product> products = productService.getAllProductsPaged(page, size, direction);

        List<ProductListResponse> productListResponses = products
                .getContent()
                .stream()
                .map(product -> {
                    var dto = new ProductListResponse();
                    dto.setProductId(product.getProductId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice());
                    dto.setStock(product.getStock());
                    return dto;
                })
                .toList();

        PagedResponse<ProductListResponse> response = new PagedResponse<>();
        response.setContent(productListResponses);
        response.setTotalElements(products.getTotalElements());
        response.setTotalPages(products.getTotalPages());
        response.setPageNumber(products.getNumber());
        response.setPageSize(products.getSize());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get product by ID",
            description = "This method returns a product by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable String productId) {
        var product = productService.getProductById(productId);
        ProductResponse productResponse = productMapper.toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @Operation(
            summary = "Update product",
            description = "This method allows the update of a product in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable String productId,
            @Parameter(description = "Product data", required = true)
            @Valid @RequestBody ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        product.setProductId(productId);

        productService.updateProduct(product);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete product",
            description = "This method allows the deletion of a product in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable String productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }

}
