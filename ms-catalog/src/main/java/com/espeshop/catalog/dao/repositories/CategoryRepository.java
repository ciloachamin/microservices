package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.CategoryExtendedRepository;
import com.espeshop.catalog.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> , CategoryExtendedRepository{
    Optional<Category> findByIdAndDeletedFalse(UUID id);
}