package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.dao.extended.ProductAttributeExtendedRepository;
import com.espeshop.catalog.model.entities.ProductAttribute;
import com.espeshop.catalog.model.keys.ProductAttributeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, ProductAttributeKey> , ProductAttributeExtendedRepository{
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductAttribute pa WHERE pa.id.productId = ?1 AND pa.id.attributeId = ?2")
    void deleteByProductIdAndAttributeId(UUID productId, UUID attributeId);
}
