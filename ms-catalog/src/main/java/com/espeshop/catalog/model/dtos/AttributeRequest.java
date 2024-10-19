package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class AttributeRequest {

    @Schema(example = "Color", description = "Nombre del Producto")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Product", description = "Nombre del Producto")
    @NotBlank(message = "El campo 'name' no puede estar vacío") @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String dataType;

}
