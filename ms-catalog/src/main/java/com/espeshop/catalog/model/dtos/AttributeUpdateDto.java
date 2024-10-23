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
public class AttributeUpdateDto {

    @Schema(example = "Capacidad", description = "Nombre del atributo del producto")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Texto", description = "Tipo de dato del atributo")
    @NotBlank(message = "El campo 'dataType' no puede estar vacío")
    @Size(max = 50, message = "El campo 'dataType' debe tener un máximo de 50 caracteres")
    private String dataType;
}