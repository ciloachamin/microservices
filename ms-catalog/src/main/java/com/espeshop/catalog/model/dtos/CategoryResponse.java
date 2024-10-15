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
public class CategoryResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private UUID parentCategoryId;
    private Boolean enabled;
    private String disabledReason;
    private Boolean deleted;
    private OffsetDateTime createdAt;
    private String createdUser;
    private OffsetDateTime updatedAt;
    private String updatedUser;
}