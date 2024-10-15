package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID de la categoría a la que pertenece el producto")
    @NotNull(message = "El campo 'categoryId' es obligatorio")
    private UUID categoryId;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174001", description = "ID de la compañía propietaria del producto")
    @NotNull(message = "El campo 'companyId' es obligatorio")
    private UUID companyId;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174002", description = "ID del usuario creador del producto")
    @NotNull(message = "El campo 'userId' es obligatorio")
    private UUID userId;

    @Schema(example = "Product", description = "Nombre del Producto")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Este es un producto especial", description = "Descripción del producto")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "BRAND", description = "Marca del Producto")
    @NotBlank(message = "El campo 'brand' no puede estar vacío")
    @Size(max = 100, message = "El campo 'brand' debe tener un máximo de 100 caracteres")
    private String brand;

    @Schema(example = "SKU12345", description = "Código SKU del producto")
    @NotBlank(message = "El campo 'skuCode' no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "El 'skuCode' solo puede contener letras, números y guiones")
    private String code;

    @Schema(example = "123456789012", description = "Código de barras del Producto")
    @Size(max = 255, message = "El código de barras no puede tener más de 255 caracteres")
    private String barcode;

    @Schema(example = "99.99", description = "Precio del Producto")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @Schema(example = "100", description = "Cantidad de productos en stock")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser al menos 0")
    private Integer stock;

    @Schema(description = "Fecha de creación de la categoría")
    private OffsetDateTime createdAt;
}
