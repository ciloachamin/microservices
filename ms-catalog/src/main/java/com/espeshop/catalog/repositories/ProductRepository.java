package com.espeshop.catalog.repositories;

import com.espeshop.catalog.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
