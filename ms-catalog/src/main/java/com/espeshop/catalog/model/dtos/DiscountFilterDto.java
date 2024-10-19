package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountFilterDto {
    private String name;
    private String discount;
    private String discountType;
    private UUID categoryId;
    private UUID productId;
    private Boolean deleted;
    private Boolean enabled;
    private UUID userId;

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                categoryId == null &&
                productId == null &&
                deleted == null &&
                enabled == null &&
                userId == null
                ;
    }
}
