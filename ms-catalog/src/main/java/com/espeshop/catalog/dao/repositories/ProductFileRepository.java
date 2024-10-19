package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ProductFileExtendedRepository;
import com.espeshop.catalog.model.entities.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductFileRepository extends JpaRepository<ProductFile, UUID> , ProductFileExtendedRepository{
    Optional<ProductFile> findByIdAndDeletedFalse(UUID id);
}
