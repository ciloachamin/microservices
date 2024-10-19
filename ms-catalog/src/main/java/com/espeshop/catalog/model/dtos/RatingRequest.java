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
public class RatingRequest {

    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID del producto que está siendo calificado")
    @NotNull(message = "El campo 'productId' es obligatorio")
    private UUID productId;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174001", description = "ID del usuario que crea la calificación")
    @NotNull(message = "El campo 'userId' es obligatorio")
    private UUID userId;

    @Schema(example = "5", description = "Calificación del producto (1 a 5)")
    @NotNull(message = "El campo 'rating' es obligatorio")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación no puede ser mayor a 5")
    private Integer rating;

    @Schema(example = "Este es un comentario sobre el producto", description = "Comentario adicional sobre la calificación")
    @Size(max = 255, message = "El comentario no puede tener más de 255 caracteres")
    private String comment;

    @Schema(example = "2024-10-01T12:00:00Z", description = "Fecha de creación de la categoría")
    private OffsetDateTime publishDate;

    @Schema(description = "Fecha de creación de la categoría")
    private OffsetDateTime createdAt;
}
