package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.RatingExtendedRepository;
import com.espeshop.catalog.model.dtos.RatingFilterDto;
import com.espeshop.catalog.model.entities.Rating;
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
public class RatingExtendedRepositoryImpl implements RatingExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Rating> findAllRatings(Pageable pageable, RatingFilterDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rating> query = cb.createQuery(Rating.class);
        //SELECT * FROM rating
        Root<Rating> root = query.from(Rating.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();

        if (filters.getRating() != null) {
            predicates.add(cb.equal(root.get("rating"), filters.getRating()));
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

        if (filters.getMinRating() != null && filters.getMaxRating() != null) {
            Predicate priceRangePredicate = cb.between(root.get("rating"), filters.getMinRating(), filters.getMaxRating());
            predicates.add(priceRangePredicate);
        } else if (filters.getMinRating() != null) {
            Predicate priceMinPredicate = cb.greaterThanOrEqualTo(root.get("rating"), filters.getMinRating());
            predicates.add(priceMinPredicate);
        } else if (filters.getMaxRating() != null) {
            Predicate priceMaxPredicate = cb.lessThanOrEqualTo(root.get("rating"), filters.getMaxRating());
            predicates.add(priceMaxPredicate);
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

        TypedQuery<Rating> typedQuery = entityManager.createQuery(query);

        return new PageImpl<>(typedQuery.getResultList(), pageable, typedQuery.getResultList().size());
    }
}
