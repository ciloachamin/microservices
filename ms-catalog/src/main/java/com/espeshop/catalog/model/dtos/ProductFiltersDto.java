package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFiltersDto {
    private String name;
    private String skuCode;
    private String stock;
    private Long dateBegin;
    private Long dateEnd;
    private String deleted;
    private Boolean enabled;
    private String userId;

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (skuCode == null || skuCode.isEmpty()) &&
                (stock == null || stock.isEmpty()) &&
                dateBegin == null &&
                dateEnd == null &&
                (deleted == null || deleted.isEmpty()) &&
                enabled == null &&
                (userId == null || userId.isEmpty())
                ;
    }
}
