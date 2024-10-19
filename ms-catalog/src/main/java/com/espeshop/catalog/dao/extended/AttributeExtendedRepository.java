package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.AttributeFilterDto;
import com.espeshop.catalog.model.entities.Attribute;

import java.util.List;

public interface AttributeExtendedRepository {
    List<Attribute> findAllAttributes(AttributeFilterDto filters);
}
