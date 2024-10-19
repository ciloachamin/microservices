package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DiscountUpdateDto {

    @Schema(example = "Tecnología", description = "Nombre de la categoría")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "Descuentos en productos electrónicos", description = "Descripción de la categoría")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "20", description = "Cantidad de descuento")
    @NotNull(message = "El discount es obligatorio")
    @Min(value = 0, message = "El discount debe ser al menos 0")
    private Integer discount;

    @Schema(example = "Porcentaje", description = "Tipo de descuento")
    @NotBlank(message = "El campo 'discountType' no puede estar vacío")
    @Size(max = 50, message = "El campo 'discountType' debe tener un máximo de 50 caracteres")
    private String discountType;

    @Schema(example = "2024-11-01T00:00:00Z", description = "Fecha de inicio del descuento")
    private OffsetDateTime startDate = OffsetDateTime.parse("2024-11-01T00:00:00Z");

    @Schema(example = "2025-01-15T23:59:59Z", description = "Fecha de fin del descuento")
    private OffsetDateTime endDate = OffsetDateTime.parse("2025-01-15T23:59:59Z");

    @Schema(example = "f47ac10b-58cc-4372-a567-0e02b2c3d479", description = "ID de la categoría padre (opcional)")
    private UUID categoryId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

    @Schema(example = "b81d43f6-e2e2-4728-b1b4-d8d432fae4b8", description = "ID del producto asociado (opcional)")
    private UUID productId = UUID.fromString("b81d43f6-e2e2-4728-b1b4-d8d432fae4b8");

    @Schema(description = "Fecha de última actualización de la categoría")
    private OffsetDateTime updatedAt;
//    @Schema(example = "JUAN", description = "Usuario que actualizó la categoría")
//    private String updatedUser;
}