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
public class RatingUpdateDto {

    @Schema(example = "5", description = "Calificación del producto (1 a 5)")
    @NotNull(message = "El campo 'rating' es obligatorio")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación no puede ser mayor a 5")
    private Integer rating;

    @Schema(example = "Este es un comentario sobre el producto", description = "Comentario adicional sobre la calificación")
    @Size(max = 255, message = "El comentario no puede tener más de 255 caracteres")
    private String comment;

    @Schema(description = "Fecha de última actualización de la categoría")
    private OffsetDateTime updatedAt;

    @Schema(example = "2024-10-01T12:00:00Z", description = "Fecha de creación de la categoría")
    private OffsetDateTime publishDate;

//    @Schema(example = "JUAN", description = "Usuario que actualizó la categoría")
//    private String updatedUser;
}