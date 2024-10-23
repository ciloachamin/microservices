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
public class ProductAttributeUpdateDto {
    @Schema(example = "Azul", description = "Marca del Producto")
    @NotBlank(message = "El campo 'value' no puede estar vacío")
    private String value;
}