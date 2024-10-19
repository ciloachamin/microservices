package com.espeshop.catalog.dao.repositories;
import com.espeshop.catalog.dao.extended.AttributeExtendedRepository;
import com.espeshop.catalog.model.dtos.AttributeFilterDto;
import com.espeshop.catalog.model.entities.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttributeRepository extends JpaRepository<Attribute, UUID>, AttributeExtendedRepository {
}
