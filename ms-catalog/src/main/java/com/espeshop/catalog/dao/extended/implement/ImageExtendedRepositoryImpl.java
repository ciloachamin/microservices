package com.espeshop.catalog.dao.extended.implement;

import com.espeshop.catalog.dao.extended.ImageExtendedRepository;
import com.espeshop.catalog.model.dtos.ImageFilterDto;
import com.espeshop.catalog.model.entities.Image;
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
public class ImageExtendedRepositoryImpl implements ImageExtendedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Image> findAllImages(ImageFilterDto filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Image> query = cb.createQuery(Image.class);
        // SELECT * FROM Image
        Root<Image> root = query.from(Image.class);

        // Lista de predicados (condiciones)
        List<Predicate> predicates = new ArrayList<>();
        
        if (filters.getProductId() != null) {
            Predicate parentImageIdPredicate =cb.equal(root.get("productId"), filters.getProductId());
            predicates.add(parentImageIdPredicate);
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

        TypedQuery<Image> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}