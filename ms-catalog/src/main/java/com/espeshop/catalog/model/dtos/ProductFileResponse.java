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
public class ProductFileResponse {
    private UUID id;
    private UUID productId;
    private String productFileUrl;
    private Boolean enabled;
    private String disabledReason;
    private Boolean deleted;
    private OffsetDateTime createdAt;
    private String createdUser;
    private OffsetDateTime updatedAt;
    private String updatedUser;
}