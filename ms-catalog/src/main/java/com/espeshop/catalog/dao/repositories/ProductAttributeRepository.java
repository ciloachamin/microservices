package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ProductAttributeExtendedRepository;
import com.espeshop.catalog.model.entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, UUID> , ProductAttributeExtendedRepository{
}
