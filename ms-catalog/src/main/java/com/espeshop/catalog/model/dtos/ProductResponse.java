package com.espeshop.catalog.model.dtos;

import com.espeshop.catalog.model.entities.ProductAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private UUID userId;
    private UUID categoryId;
    private String categoryName;
    private UUID companyId;
    private String name;
    private String slug;
    private String brand;
    private BigDecimal price;
    private Integer stock;
    private Integer rating;
    private String description;
    private String code;
    private String barcode;
    private Boolean enabled;
    private String disabledReason;
    private Boolean deleted;
    private OffsetDateTime createdAt;
    private String createdUser;
    private OffsetDateTime updatedAt;
    private String updatedUser;

    private Set<ImageResponse> images;
    private Set<ProductFileResponse> productFiles;
    private Set<AttributeResponse> attributes;


}
