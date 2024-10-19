package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.AttributeExtendedRepository;
import com.espeshop.catalog.model.dtos.AttributeFilterDto;
import com.espeshop.catalog.model.entities.Attribute;
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
public class AttributeExtendedRepositoryImpl implements AttributeExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Attribute> findAllAttributes(AttributeFilterDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attribute> query = cb.createQuery(Attribute.class);
        // SELECT * FROM Attribute
        Root<Attribute> root = query.from(Attribute.class);
        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        if (filters.getName() != null) {
            Predicate namePredicate = cb.like(root.get("name"), "%" + filters.getName() + "%");
            predicates.add(namePredicate);
        }
        if (filters.getDataType() != null) {
            Predicate parentAttributeIdPredicate =cb.equal(root.get("parentAttributeId"), filters.getDataType());
            predicates.add(parentAttributeIdPredicate);
        }
        query.where(
                cb.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Attribute> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}