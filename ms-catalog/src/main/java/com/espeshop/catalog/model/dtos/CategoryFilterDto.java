package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFilterDto {
    private String name;
    private UUID parentCategoryId;
    private Boolean deleted;
    private Boolean enabled;
    private UUID userId;

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                parentCategoryId == null &&
                deleted == null &&
                enabled == null &&
                userId == null
                ;
    }
}
