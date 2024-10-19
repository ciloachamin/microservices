package com.espeshop.catalog.model.dtos;

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
public class DiscountResponse {
    private UUID id;
    private String name;
    private Integer discount;
    private String description;
    private String discountType;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private UUID categoryId;
    private UUID productId;
    private Boolean enabled;
    private String disabledReason;
    private Boolean deleted;
    private OffsetDateTime createdAt;
    private String createdUser;
    private OffsetDateTime updatedAt;
    private String updatedUser;
}