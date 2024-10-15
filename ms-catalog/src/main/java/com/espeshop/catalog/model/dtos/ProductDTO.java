package com.espeshop.catalog.model.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
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