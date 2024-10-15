package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ProductExtendedRepository;
import com.espeshop.catalog.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductExtendedRepository {

    Optional<Product> findByName(String productName);


}
