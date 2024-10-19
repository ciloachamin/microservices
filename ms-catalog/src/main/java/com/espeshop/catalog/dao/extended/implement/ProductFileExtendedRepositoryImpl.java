package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.ProductFileExtendedRepository;
import com.espeshop.catalog.model.dtos.ProductFileFilterDto;
import com.espeshop.catalog.model.entities.ProductFile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Repository
@RequiredArgsConstructor
public class ProductFileExtendedRepositoryImpl implements ProductFileExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductFile> findAllProductFiles(ProductFileFilterDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductFile> query = cb.createQuery(ProductFile.class);
        // SELECT * FROM ProductFile
        Root<ProductFile> root = query.from(ProductFile.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        
        if (filters.getProductId() != null) {
            Predicate parentProductFileIdPredicate =cb.equal(root.get("productId"), filters.getProductId());
            predicates.add(parentProductFileIdPredicate);
        }
        
        if (filters.getDeleted() != null) {
            predicates.add(cb.equal(root.get("deleted"), filters.getDeleted()));
        }

        if (filters.getEnabled() != null) {
            predicates.add(cb.equal(root.get("enabled"), filters.getEnabled()));
        }

        query.where(
                cb.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<ProductFile> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}