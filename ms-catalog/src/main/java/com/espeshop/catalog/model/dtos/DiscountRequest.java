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
public class DiscountRequest {

    @Schema(example = "Descuento de verano", description = "Nombre del descuento")
    @NotBlank(message = "El campo 'name' no puede estar vacío")
    @Size(max = 50, message = "El campo 'name' debe tener un máximo de 50 caracteres")
    private String name;

    @Schema(example = "50", description = "Cantidad de descuento")
    @NotNull(message = "El discount es obligatorio")
    @Min(value = 0, message = "El discount debe ser al menos 0")
    private Integer discount;

    @Schema(example = "Descuento aplicado en productos seleccionados de la temporada", description = "Descripción del descuento")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @Schema(example = "Porcentaje", description = "Tipo de descuento")
    @NotBlank(message = "El campo 'discountType' no puede estar vacío")
    @Size(max = 50, message = "El campo 'discountType' debe tener un máximo de 50 caracteres")
    private String discountType;

    @Schema(example = "2024-10-01T08:00:00Z", description = "Fecha de inicio del descuento")
    private OffsetDateTime startDate = OffsetDateTime.parse("2024-10-01T08:00:00Z");

    @Schema(example = "2024-12-31T23:59:59Z", description = "Fecha de fin del descuento")
    private OffsetDateTime endDate = OffsetDateTime.parse("2024-12-31T23:59:59Z");

    @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", description = "ID de la categoría padre (opcional)")
    private UUID categoryId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");

    @Schema(example = "a82f5f4b-d5d4-4e4a-96fc-77db24673a2f", description = "ID del producto asociado (opcional)")
    private UUID productId = UUID.fromString("a82f5f4b-d5d4-4e4a-96fc-77db24673a2f");

    @Schema(description = "Fecha de creación de la categoría")
    private OffsetDateTime createdAt;
}