package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.DiscountExtendedRepository;
import com.espeshop.catalog.model.dtos.DiscountFilterDto;
import com.espeshop.catalog.model.entities.Discount;
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
public class DiscountExtendedRepositoryImpl implements DiscountExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Discount> findAllDiscounts(DiscountFilterDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Discount> query = cb.createQuery(Discount.class);
        // SELECT * FROM Discount
        Root<Discount> root = query.from(Discount.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        if (filters.getName() != null) {
            Predicate namePredicate = cb.like(root.get("name"), "%" + filters.getName() + "%");
            predicates.add(namePredicate);
        }

        if (filters.getDiscountType() != null) {
            Predicate discountTypePredicate = cb.like(root.get("discountType"), "%" + filters.getDiscountType() + "%");
            predicates.add(discountTypePredicate);
        }

        if (filters.getDiscount() != null) {
            Predicate discountPredicate = cb.like(root.get("discount"), filters.getDiscount());
            predicates.add(discountPredicate);
        }

        if (filters.getCategoryId() != null) {
            Predicate categotyIdPredicate =cb.equal(root.get("categoryId"), filters.getCategoryId());
            predicates.add(categotyIdPredicate);
        }

        if (filters.getProductId() != null) {
            Predicate productIdPredicate =cb.equal(root.get("categoryId"), filters.getProductId());
            predicates.add(productIdPredicate);
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

        TypedQuery<Discount> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}