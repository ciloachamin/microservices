package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ProductExtendedRepository;
import com.espeshop.catalog.model.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductExtendedRepository {
    Optional<Product> findByName(String productName);
    Optional<Product> findByIdAndDeletedFalse(UUID id);
    @EntityGraph(attributePaths = {"images","productFiles"})
    Optional<Product> findById(UUID id);

}
