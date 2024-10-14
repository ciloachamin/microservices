package com.espeshop.catalog.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
        @Schema(example = "65aedff0c826ba6710e7e799", description = "Especialidad")
        @Size(max = 50, message = "El campo 'especiality_id' debe ser un string")
        String name,
        String skuCode,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        LocalDateTime orderDateSince,
        LocalDateTime orderDateUntil,
        LocalDateTime deliveryDateSince,
        LocalDateTime deliveryDateUntil,
        int page,
        int size
) {
}