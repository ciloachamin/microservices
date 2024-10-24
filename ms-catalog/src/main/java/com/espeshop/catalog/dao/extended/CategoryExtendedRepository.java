package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.CategoryFilterDto;
import com.espeshop.catalog.model.entities.Category;

import java.util.List;

public interface CategoryExtendedRepository {
    List<Category> findAllCategories(CategoryFilterDto filters);
}
