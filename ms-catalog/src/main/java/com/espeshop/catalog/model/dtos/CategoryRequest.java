package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    @Schema(example = "Electrónica", description = "Nombre de la categoría")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "categoría-electrónica", description = "Slug para la categoría")
    private String slug;

    @Schema(example = "Categoría para productos electrónicos", description = "Descripción de la categoría")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "Imagen URL", description = "URL de la imagen de la categoría")
    private String image;

    @Schema(example = "true", description = "Estado de la categoría (true si está activa)")
    private Boolean enabled;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID de la categoría padre (opcional)")
    private String parentCategoryId; // Puedes cambiar el tipo según tus necesidades

    @Schema(example = "Razón por la que la categoría está deshabilitada", description = "Razón por la que está deshabilitada")
    private String disabledReason;

    @Schema(example = "false", description = "Indica si la categoría está eliminada")
    private boolean deleted;

    @Schema(description = "Fecha de creación de la categoría")
    private OffsetDateTime createdAt;

    @Schema(example = "admin", description = "Usuario que creó la categoría")
    private String createdUser;

    @Schema(description = "Fecha de última actualización de la categoría")
    private OffsetDateTime updatedAt;

    @Schema(example = "admin", description = "Usuario que actualizó la categoría")
    private String updatedUser;
}