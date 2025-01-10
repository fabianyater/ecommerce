package com.pruebatecnica.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Schema(description = "Product request information")
public class ProductRequest {
    @Schema(description = "Product name", example = "Smartphone", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre del producto es requerido")
    @Size(min = 3, max = 50, message = "El nombre del producto debe tener entre 3 y 50 caracteres")
    private String name;

    @Schema(description = "Product description", example = "Smartphone with 6.5-inch screen", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 255, message = "La descripción del producto debe tener máximo 255 caracteres")
    private String description;

    @Schema(description = "Product price", example = "500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio del producto es requerido")
    @DecimalMin(value = "0.0", message = "El precio del producto debe ser mayor a 0")
    private BigDecimal price;

    @Schema(description = "Product stock", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(value = 0, message = "El stock del producto debe ser mayor o igual a 0")
    private Integer stock;
}
