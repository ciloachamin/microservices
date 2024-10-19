package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ImageExtendedRepository;
import com.espeshop.catalog.model.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> , ImageExtendedRepository{
    Optional<Image> findByIdAndDeletedFalse(UUID id);
}
