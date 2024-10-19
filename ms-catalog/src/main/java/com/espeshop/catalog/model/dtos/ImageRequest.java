package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ImageRequest {

    @Schema(example = "https://images", description = "Url de la imagen")
    private String imageUrl;

    @Schema(example = "ae7195f7-4bb4-44bd-a998-5a88b3ef6408", description = "ID del producto")
    private String productId;

    @Schema(description = "Fecha de creación de la imagen")
    private OffsetDateTime createdAt;
}