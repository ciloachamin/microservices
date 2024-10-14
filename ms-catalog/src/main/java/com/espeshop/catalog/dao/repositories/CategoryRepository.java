package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}