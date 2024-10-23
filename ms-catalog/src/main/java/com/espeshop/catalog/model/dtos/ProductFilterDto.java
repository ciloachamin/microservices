package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDto {
    private OffsetDateTime dateBegin;
    private OffsetDateTime dateEnd;
    private String name;
    private String code;
    private Integer stock;
    private Integer rating;
    private String brand;
    private String barcode;
    private Boolean deleted;
    private Boolean enabled;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Integer stockMin;
    private Integer stockMax;
    private UUID categoryId;
    private UUID companyId;
    private UUID userId;



    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (code == null || code.isEmpty()) &&
                stock == null  &&
                rating == null &&
                priceMin == null &&
                priceMax == null &&
                categoryId == null &&
                (brand == null || brand.isEmpty()) &&
                stockMin == null &&
                stockMax == null &&
                (barcode == null || barcode.isEmpty()) &&
                companyId == null &&
                dateBegin == null &&
                dateEnd == null &&
                deleted == null &&
                enabled == null &&
                userId == null
                ;
    }
}
