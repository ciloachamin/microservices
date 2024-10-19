package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateDto {
    @Schema(example = "Alimentos", description = "Nombre de la categoría")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Categoría para Alimentos", description = "Descripción de la categoría")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "Imagen URL", description = "URL de la imagen de la categoría")
    private String image;

    @Schema(example = "ae7195f7-4bb4-44bd-a998-5a88b3ef6408", description = "ID de la categoría padre (opcional)")
    private String parentCategoryId;

    @Schema(description = "Fecha de última actualización de la categoría")
    private OffsetDateTime updatedAt;

//    @Schema(example = "JUAN", description = "Usuario que actualizó la categoría")
//    private String updatedUser;
}