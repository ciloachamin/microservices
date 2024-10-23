package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateDto {

    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID de la categoría a la que pertenece el producto")
    private UUID categoryId;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174001", description = "ID de la compañía propietaria del producto")
    private UUID companyId;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174002", description = "ID del usuario que actualiza el producto")
    private UUID userId;

    @Schema(example = "Alimentos", description = "Nombre de la categoría")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Categoría para Alimentos", description = "Descripción de la categoría")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "Marca actualizada", description = "Marca del Producto")
    @Size(max = 100, message = "El campo 'brand' debe tener un máximo de 100 caracteres")
    private String brand;

    @Schema(example = "SKU12345", description = "Código SKU del Producto")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "El 'skuCode' solo puede contener letras, números y guiones")
    private String code;

    @Schema(example = "99.99", description = "Precio del Producto")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @Schema(example = "100", description = "Cantidad de productos en stock")
    @Min(value = 0, message = "El stock debe ser al menos 0")
    private Integer stock;

    @Schema(example = "5", description = "Calificación del producto (1 a 5)")
    @NotNull(message = "El campo 'rating' es obligatorio")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación no puede ser mayor a 5")
    private Integer rating;


    @Schema(description = "Fecha de última actualización de la categoría")
    private OffsetDateTime updatedAt;

    //    @Schema(example = "JUAN", description = "Usuario que actualizó la categoría")
//    private String updatedUser;
// Nuevo campo para las URLs de imágenes
    @Schema(description = "Lista de URLs de imágenes del producto")
    private List<String> imageUrls;

    // Nuevo campo para las URLs de imágenes
    @Schema(description = "Lista de URLs de Archivos del producto")
    private List<String> productFileUrls;

    // Nuevo campo para los atributos del producto
    @Schema(description = "Lista de atributos del producto")
    private List<AttributeRequest> attributes;  // Este campo contendrá los atributos del producto
}