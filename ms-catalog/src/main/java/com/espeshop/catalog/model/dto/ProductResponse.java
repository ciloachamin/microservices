package com.espeshop.catalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String skuCode;
    private Double price;
    private Integer stock;
}
