package com.espeshop.catalog.model.dtos;

import com.espeshop.catalog.model.entities.Attribute;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.model.keys.ProductAttributeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeResponse {
    private ProductAttributeKey id;
    private Attribute attributeId;
    private Product productId;
    private String value;
}
