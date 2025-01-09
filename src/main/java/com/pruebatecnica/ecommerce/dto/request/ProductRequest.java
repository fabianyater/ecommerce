package com.pruebatecnica.ecommerce.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "El nombre del producto es requerido")
    @Size(min = 3, max = 50, message = "El nombre del producto debe tener entre 3 y 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción del producto debe tener máximo 255 caracteres")
    private String description;

    @NotNull(message = "El precio del producto es requerido")
    @DecimalMin(value = "0.0", message = "El precio del producto debe ser mayor a 0")
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = "El stock del producto debe ser mayor o igual a 0")
    private Integer stock;
}
