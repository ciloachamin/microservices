package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @Schema(example = "Product", description = "Nombre del Producto")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Este es un producto especial", description = "Descripción del producto")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "SKU12345", description = "Código SKU del producto")
    @NotBlank(message = "El campo 'skuCode' no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "El 'skuCode' solo puede contener letras, números y guiones")
    private String skuCode;

    @Schema(example = "99.99", description = "Precio del producto")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private Double price;

    @Schema(example = "100", description = "Cantidad de productos en stock")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser al menos 0")
    private Integer stock;
}
