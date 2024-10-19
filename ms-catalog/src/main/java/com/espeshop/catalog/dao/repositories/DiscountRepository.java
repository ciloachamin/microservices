package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.DiscountExtendedRepository;
import com.espeshop.catalog.model.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> , DiscountExtendedRepository{
    Optional<Discount> findByIdAndDeletedFalse(UUID id);
}
