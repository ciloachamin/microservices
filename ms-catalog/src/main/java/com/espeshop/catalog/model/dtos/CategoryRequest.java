package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    @Schema(example = "Electrónica", description = "Nombre de la categoría")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Categoría para productos electrónicos", description = "Descripción de la categoría")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "Imagen URL", description = "URL de la imagen de la categoría")
    private String image;

    @Schema(example = "null", description = "ID de la categoría padre (opcional)")
    private UUID parentCategoryId;

    @Schema(description = "Fecha de creación de la categoría")
    private OffsetDateTime createdAt;
}