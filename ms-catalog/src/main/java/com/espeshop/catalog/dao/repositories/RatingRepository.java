package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.RatingExtendedRepository;
import com.espeshop.catalog.model.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Long>, RatingExtendedRepository {
    Optional<Rating> findByIdAndDeletedFalse(UUID id);
}
