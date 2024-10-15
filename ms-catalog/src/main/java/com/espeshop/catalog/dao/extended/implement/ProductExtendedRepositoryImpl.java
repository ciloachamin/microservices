package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.ProductExtendedRepository;
import com.espeshop.catalog.model.dtos.FilterProductDto;
import com.espeshop.catalog.model.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Repository
@RequiredArgsConstructor
public class ProductExtendedRepositoryImpl implements ProductExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findAllProducts(Pageable pageable, FilterProductDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        //SELECT * FROM product
        Root<Product> root = query.from(Product.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        if (filters.getName() != null) {
            Predicate namePredicate = cb.like(root.get("name"), "%" + filters.getName() + "%");
            predicates.add(namePredicate);
        }
        if (filters.getSkuCode() != null) {
            Predicate skuCodePredicate = cb.like(root.get("skuCode"), "%" + filters.getSkuCode() + "%");
            predicates.add(skuCodePredicate);
        }
        if (filters.getStock() != null) {
            Predicate stokePredicate = cb.like(root.get("stock"), filters.getStock());
            predicates.add(stokePredicate);
        }

        if (filters.getDateBegin() != null && filters.getDateEnd() != null) {
            Predicate dateRangePredicate = cb.between(root.get("createdAt"), filters.getDateBegin(), filters.getDateEnd());
            predicates.add(dateRangePredicate);
        } else if (filters.getDateBegin() != null) {
            Predicate dateBeginPredicate = cb.greaterThanOrEqualTo(root.get("createdAt"), filters.getDateBegin());
            predicates.add(dateBeginPredicate);
        } else if (filters.getDateEnd() != null) {
            Predicate dateEndPredicate = cb.lessThanOrEqualTo(root.get("createdAt"), filters.getDateEnd());
            predicates.add(dateEndPredicate);
        }
        if (filters.getDeleted() != null) {
            predicates.add(cb.equal(root.get("deleted"), filters.getDeleted()));
        }

        if (filters.getEnabled() != null) {
            predicates.add(cb.equal(root.get("enabled"), filters.getEnabled()));
        }

        if (filters.getUserId() != null) {
            predicates.add(cb.equal(root.get("userId"), filters.getUserId()));
        }
        query.where(
                cb.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        return new PageImpl<>(typedQuery.getResultList(), pageable, typedQuery.getResultList().size());

    }
}
