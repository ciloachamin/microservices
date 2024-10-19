package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.ProductFileFilterDto;
import com.espeshop.catalog.model.entities.ProductFile;

import java.util.List;

public interface ProductFileExtendedRepository {
    List<ProductFile> findAllProductFiles(ProductFileFilterDto filters);
}
