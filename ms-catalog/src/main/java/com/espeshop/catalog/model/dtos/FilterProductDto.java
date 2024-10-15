package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterProductDto {
    private String name;
    private String skuCode;
    private String stock;
    private OffsetDateTime dateBegin;
    private OffsetDateTime dateEnd;
    private Boolean deleted;
    private Boolean enabled;
    private UUID userId;

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (skuCode == null || skuCode.isEmpty()) &&
                (stock == null || stock.isEmpty()) &&
                dateBegin == null &&
                dateEnd == null &&
                deleted == null &&
                enabled == null &&
                userId == null
                ;
    }
}
