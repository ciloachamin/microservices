package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeRequest {
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID del atributo ")
    @NotNull(message = "El campo 'attributeId' es obligatorio")
    private UUID attributeId;

    @Schema(example = "123e4567-e89b-12d3-a456-426614174001", description = "ID del producto ")
    @NotNull(message = "El campo 'productId' es obligatorio")
    private UUID productId;

    @Schema(example = "Rojo", description = "Valor del atributo")
    @NotBlank(message = "El campo 'value' no puede estar vacío")
    private String value;
}
