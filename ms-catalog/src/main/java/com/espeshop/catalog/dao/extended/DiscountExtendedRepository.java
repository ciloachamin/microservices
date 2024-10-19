package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.DiscountFilterDto;
import com.espeshop.catalog.model.entities.Discount;

import java.util.List;

public interface DiscountExtendedRepository {
    List<Discount> findAllDiscounts(DiscountFilterDto filters);
}
