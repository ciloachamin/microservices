package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.ProductRepository;
import com.espeshop.catalog.dao.repositories.RatingRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.RatingFilterDto;
import com.espeshop.catalog.model.dtos.RatingRequest;
import com.espeshop.catalog.model.dtos.RatingResponse;
import com.espeshop.catalog.model.dtos.RatingUpdateDto;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.model.entities.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;

    public RatingResponse createRating(RatingRequest ratingRequest) {
        Product product = productService.getProductById(ratingRequest.getProductId());
        Rating rating = Rating.builder()
                .rating(ratingRequest.getRating())
                .comment(ratingRequest.getComment())
                .publishDate(ratingRequest.getPublishDate())
                .userId(ratingRequest.getUserId())
                .product(product)
                .createdAt(ratingRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        Rating savedRating = ratingRepository.save(rating);
        return mapToRatingResponse(savedRating);
    }

    public Page<RatingResponse> getAllRatings(Pageable pageable, RatingFilterDto filters) {
        Page<Rating> ratings;
        if (filters == null || filters.isEmpty()) {
            ratings = ratingRepository.findAll(pageable);
        } else {
            ratings = ratingRepository.findAllRatings(pageable, filters);
        }
        return ratings.map(this::mapToRatingResponse);
    }

    public Rating getRatingById(UUID id) {
        return ratingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "id", id));
    }

    public RatingResponse updateRating(UUID id, RatingUpdateDto ratingUpdateDto) {
        Rating rating = ratingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "id", id));
        rating.setRating(ratingUpdateDto.getRating());
        rating.setComment(ratingUpdateDto.getComment());
        rating.setPublishDate(ratingUpdateDto.getPublishDate());
        rating.setUpdatedAt(ratingUpdateDto.getUpdatedAt());
        rating.setUpdatedUser("MARIA PEREZ");
        ratingRepository.save(rating);
        return mapToRatingResponse(rating);}

    public RatingResponse deleteRating(UUID id) {
        Rating rating = ratingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "id", id));
        rating.setDeleted(true);
        Rating savedRating = ratingRepository.save(rating);
        return mapToRatingResponse(savedRating);
    }

    private RatingResponse mapToRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .productId(rating.getProduct() != null ? rating.getProduct().getId() : null)
                .userId(rating.getUserId())
                .rating(rating.getRating())
                .comment(rating.getComment())
                .publishDate(rating.getPublishDate())
                .enabled(rating.getEnabled())
                .disabledReason(rating.getDisabledReason())
                .deleted(rating.getDeleted())
                .createdAt(rating.getCreatedAt())
                .createdUser(rating.getCreatedUser())
                .updatedAt(rating.getUpdatedAt())
                .updatedUser(rating.getUpdatedUser())
                .build();
    }

}
