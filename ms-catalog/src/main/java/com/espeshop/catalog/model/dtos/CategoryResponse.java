package com.espeshop.catalog.model.dtos;

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
public class CategoryResponse {
    private UUID id; // ID de la categoría
    private String name; // Nombre de la categoría
    private String slug; // Slug de la categoría
    private UUID parentCategoryId; // ID de la categoría padre (opcional)
    private String disabledReason; // Razón por la que la categoría está deshabilitada
    private boolean deleted; // Indica si la categoría está eliminada
    private OffsetDateTime createdAt; // Fecha de creación de la categoría
    private String createdUser; // Usuario que creó la categoría
    private OffsetDateTime updatedAt; // Fecha de última actualización de la categoría
    private String updatedUser; // Usuario que actualizó la categoría
    private String description; // Descripción de la categoría (opcional)
    private Boolean enabled; // Estado de la categoría (activa/inactiva)
    private String image; // URL de la imagen de la categoría (opcional)

}