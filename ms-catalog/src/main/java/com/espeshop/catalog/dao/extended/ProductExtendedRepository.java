package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.ProductFilterDto;
import com.espeshop.catalog.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductExtendedRepository {
    Page<Product> findAllProducts(Pageable pageable, ProductFilterDto filters);
}
