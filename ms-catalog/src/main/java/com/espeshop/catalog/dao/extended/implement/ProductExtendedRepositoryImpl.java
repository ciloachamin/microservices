package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.ProductExtendedRepository;
import com.espeshop.catalog.model.dtos.ProductFilterDto;
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
    public Page<Product> findAllProducts(Pageable pageable, ProductFilterDto filters) {
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

        if (filters.getCode() != null) {
            Predicate codePredicate = cb.like(root.get("code"), "%" + filters.getCode() + "%");
            predicates.add(codePredicate);
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

        if (filters.getPriceMin() != null && filters.getPriceMax() != null) {
            Predicate priceRangePredicate = cb.between(root.get("price"), filters.getPriceMin(), filters.getPriceMax());
            predicates.add(priceRangePredicate);
        } else if (filters.getPriceMin() != null) {
            Predicate priceMinPredicate = cb.greaterThanOrEqualTo(root.get("price"), filters.getPriceMin());
            predicates.add(priceMinPredicate);
        } else if (filters.getPriceMax() != null) {
            Predicate priceMaxPredicate = cb.lessThanOrEqualTo(root.get("price"), filters.getPriceMax());
            predicates.add(priceMaxPredicate);
        }

        if (filters.getStockMin() != null && filters.getStockMax() != null) {
            Predicate stockRangePredicate = cb.between(root.get("stock"), filters.getStockMin(), filters.getStockMax());
            predicates.add(stockRangePredicate);
        } else if (filters.getStockMin() != null) {
            Predicate stockMinPredicate = cb.greaterThanOrEqualTo(root.get("stock"), filters.getStockMin());
            predicates.add(stockMinPredicate);
        } else if (filters.getStockMax() != null) {
            Predicate stockMaxPredicate = cb.lessThanOrEqualTo(root.get("stock"), filters.getStockMax());
            predicates.add(stockMaxPredicate);
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

        if (filters.getCategoryId() != null) {
            predicates.add(cb.equal(root.get("categoryId"), filters.getCategoryId()));
        }
        if (filters.getCompanyId() != null) {
            predicates.add(cb.equal(root.get("companyId"), filters.getCompanyId()));
        }
        if (filters.getBrand() != null) {
            predicates.add(cb.equal(root.get("brand"), filters.getBrand()));
        }

        if (filters.getBarcode() != null) {
            predicates.add(cb.equal(root.get("barcode"), filters.getBarcode()));
        }

        query.where(
                cb.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        return new PageImpl<>(typedQuery.getResultList(), pageable, typedQuery.getResultList().size());
    }
}
