package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeFilterDto {
    private UUID attributeId;
    private UUID productId;



    public boolean isEmpty() {
        return productId == null &&
                attributeId == null

                ;
    }
}
