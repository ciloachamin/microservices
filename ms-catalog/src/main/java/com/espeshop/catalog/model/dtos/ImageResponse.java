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
public class ImageResponse {
    private UUID id;
    private UUID productId;
    private String imageUrl;
    private Boolean enabled;
    private String disabledReason;
    private Boolean deleted;
    private OffsetDateTime createdAt;
    private String createdUser;
    private OffsetDateTime updatedAt;
    private String updatedUser;
}