package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.ProductAttributeFilterDto;
import com.espeshop.catalog.model.entities.ProductAttribute;

import java.util.List;

public interface ProductAttributeExtendedRepository {
    List<ProductAttribute> findAllProductsAttributes(ProductAttributeFilterDto filters);
}
