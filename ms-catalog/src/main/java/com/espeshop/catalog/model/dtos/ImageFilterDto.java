package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFilterDto {
    private UUID productId;
    private Boolean deleted;
    private Boolean enabled;

    public boolean isEmpty() {
        return
                productId == null &&
                deleted == null &&
                enabled == null
                ;
    }
}
