package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.RatingFilterDto;
import com.espeshop.catalog.model.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingExtendedRepository {
    Page<Rating> findAllRatings(Pageable pageable, RatingFilterDto filters);
}
