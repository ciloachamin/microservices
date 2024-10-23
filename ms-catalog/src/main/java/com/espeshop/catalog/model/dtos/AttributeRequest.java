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

    @Schema(example = "Color", description = "Nombre del atributo del producto")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Rojo", description = "Valor del atributo para el producto")
    @NotBlank(message = "El campo 'value' no puede estar vacío")
    @Size(max = 255, message = "El valor del atributo debe tener un máximo de 255 caracteres")
    private String value;

    @Schema(example = "text", description = "Tipo de dato del atributo")
    @NotBlank(message = "El campo 'dataType' no puede estar vacío")
    @Size(max = 50, message = "El campo 'dataType' debe tener un máximo de 50 caracteres")
    private String dataType;

}
