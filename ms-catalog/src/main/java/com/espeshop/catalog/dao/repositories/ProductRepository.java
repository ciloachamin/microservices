package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ProductExtendedRepository;
import com.espeshop.catalog.model.dtos.ProductDTO;
import com.espeshop.catalog.model.dtos.ProductRequest;
import com.espeshop.catalog.model.dtos.ProductResponse;
import com.espeshop.catalog.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductExtendedRepository {

    Optional<Product> findByName(String productName);


}
