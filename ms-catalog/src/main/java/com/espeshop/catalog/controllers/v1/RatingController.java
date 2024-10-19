package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Rating;
import com.espeshop.catalog.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "Operations related to Ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/Rating")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new Rating",
            description = "This endpoint allows administrators to create a new Rating. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<RatingResponse>> createRating(@RequestBody @Valid RatingRequest RatingRequest , HttpServletRequest request) {
        RatingResponse createdRating = ratingService.createRating(RatingRequest);
        CustomApiResponse<RatingResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Rating created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdRating
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/Ratings")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all Ratings",
            description = "Retrieve a list of all Ratings. You can optionally filter by name.",
            parameters = {
                    @Parameter(name = "page", required = true),
                    @Parameter(name = "size", required = true),
                    @Parameter(name = "rating", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
                    @Parameter(name = "dateBegin", required = false),
                    @Parameter(name = "dateEnd", required = false),
                    @Parameter(name = "maxRating", required = false),
                    @Parameter(name = "minRating", required = false),
                    @Parameter(name = "productId", required = false),
                    @Parameter(name = "userId", required = false),
            }
    )
    public ResponseEntity<CustomApiResponse<Page<RatingResponse>>> getAllRatings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) OffsetDateTime dateBegin,
            @RequestParam(required = false) OffsetDateTime dateEnd,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID userId,
            HttpServletRequest request
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        RatingFilterDto filters = new RatingFilterDto(
                dateBegin,
                dateEnd,
                rating,
                maxRating,
                minRating,
                deleted,
                enabled,
                productId,
                userId
        );
        Page<RatingResponse> Ratings = ratingService.getAllRatings(pageable, filters);
        CustomApiResponse<Page<RatingResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Ratings retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                Ratings
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("Rating/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing Rating",
            description = "This endpoint allows administrators to update an existing Rating. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<RatingResponse>> updateRating(
            @PathVariable UUID id,
            @RequestBody @Valid RatingUpdateDto RatingUpdateDto,
            HttpServletRequest request
    ) {
        RatingResponse updatedRating = ratingService.updateRating(id, RatingUpdateDto);
        CustomApiResponse<RatingResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Rating updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedRating
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("Rating/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a Rating",
            description = "This endpoint allows administrators to delete a Rating. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<RatingResponse>> deleteRating(
            @PathVariable UUID id,
            HttpServletRequest request) {
        RatingResponse deletedRating = ratingService.deleteRating(id);
        CustomApiResponse<RatingResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Rating deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedRating
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("Rating/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get Rating by ID",
            description = "Retrieve a Rating by its ID."
    )
    public ResponseEntity<CustomApiResponse<Rating>> getRatingById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        Rating RatingResponse = ratingService.getRatingById(id);
        CustomApiResponse<Rating> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Rating retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                RatingResponse
        );
        return ResponseEntity.ok(response);
    }
}