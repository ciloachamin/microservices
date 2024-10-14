package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.ProductFiltersDto;
import com.espeshop.catalog.model.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductExtendedRepository {
    Page<Product> findAllProducts(Pageable pageable, ProductFiltersDto filters);
}
