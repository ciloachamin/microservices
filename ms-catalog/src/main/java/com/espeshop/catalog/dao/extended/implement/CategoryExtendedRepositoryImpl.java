package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.CategoryExtendedRepository;
import com.espeshop.catalog.model.dtos.FilterCategoryDto;
import com.espeshop.catalog.model.entities.Category;
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
public class CategoryExtendedRepositoryImpl implements CategoryExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> findAllCategories(FilterCategoryDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        // SELECT * FROM Category
        Root<Category> root = query.from(Category.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        if (filters.getName() != null) {
            Predicate namePredicate = cb.like(root.get("name"), "%" + filters.getName() + "%");
            predicates.add(namePredicate);
        }

        if (filters.getParentCategoryId() != null) {
            Predicate parentCategoryIdPredicate =cb.equal(root.get("parentCategoryId"), filters.getParentCategoryId());
            predicates.add(parentCategoryIdPredicate);
        }

        if (filters.getUserId() != null) {
            Predicate userIdPredicate = cb.like(root.get("userId"), "%" + filters.getUserId() + "%");
            predicates.add(userIdPredicate);
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

        TypedQuery<Category> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}