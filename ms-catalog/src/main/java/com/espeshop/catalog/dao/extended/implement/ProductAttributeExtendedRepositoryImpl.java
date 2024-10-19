package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.ProductAttributeExtendedRepository;
import com.espeshop.catalog.model.dtos.ProductAttributeFilterDto;
import com.espeshop.catalog.model.entities.ProductAttribute;
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
public class ProductAttributeExtendedRepositoryImpl implements ProductAttributeExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductAttribute> findAllProductsAttributes(ProductAttributeFilterDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductAttribute> query = cb.createQuery(ProductAttribute.class);
        // SELECT * FROM ProductAttribute
        Root<ProductAttribute> root = query.from(ProductAttribute.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        if (filters.getAttributeId() != null) {
            Predicate parentProductAttributeIdPredicate =cb.equal(root.get("attributeId"), filters.getAttributeId());
            predicates.add(parentProductAttributeIdPredicate);
        }

        if (filters.getProductId() != null) {
            Predicate parentProductAttributeIdPredicate =cb.equal(root.get("productId"), filters.getProductId());
            predicates.add(parentProductAttributeIdPredicate);
        }


        query.where(
                cb.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<ProductAttribute> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}