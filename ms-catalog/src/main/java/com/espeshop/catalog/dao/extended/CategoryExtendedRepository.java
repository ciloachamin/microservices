package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.FilterCategoryDto;
import com.espeshop.catalog.model.dtos.FilterProductDto;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryExtendedRepository {
    List<Category> findAllCategorys(FilterCategoryDto filters);
}
