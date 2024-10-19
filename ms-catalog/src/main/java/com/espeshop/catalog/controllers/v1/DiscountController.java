package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Discount;
import com.espeshop.catalog.services.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Discount", description = "Operations related to discounts")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/discount")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new discount",
            description = "This endpoint allows administrators to create a new discount. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<DiscountResponse>> createDiscount(@RequestBody @Valid DiscountRequest discountRequest, HttpServletRequest request) {
        DiscountResponse createdDiscount = discountService.createDiscount(discountRequest);
        CustomApiResponse<DiscountResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Discount created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdDiscount
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/discounts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all discounts",
            description = "Retrieve a list of all discounts.",
            parameters = {
                    @Parameter(name = "name", required = false),
                    @Parameter(name = "discount", required = false),
                    @Parameter(name = "discountType", required = false),
                    @Parameter(name = "categoryId", required = false),
                    @Parameter(name = "productId", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
                    @Parameter(name = "userId", required = false)
            }
    )
    public ResponseEntity<CustomApiResponse<List<DiscountResponse>>> getAllDiscounts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String discount,
            @RequestParam(required = false) String discountType,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) UUID userId,
            HttpServletRequest request
    ) {
        DiscountFilterDto filters = new DiscountFilterDto(name, discount,discountType,categoryId,productId, deleted, enabled, userId);
        List<DiscountResponse> discounts = discountService.getAllDiscounts(filters);
        CustomApiResponse<List<DiscountResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Discounts retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                discounts
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/discount/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get discount by ID",
            description = "Retrieve a discount by its ID."
    )
    public ResponseEntity<CustomApiResponse<Discount>> getDiscountById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        Discount discountResponse = discountService.getDiscountById(id);
        CustomApiResponse<Discount> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Discount retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                discountResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("discount/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing discount",
            description = "This endpoint allows administrators to update an existing discount. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<DiscountResponse>> updateDiscount(
            @PathVariable UUID id,
            @RequestBody @Valid DiscountUpdateDto discountUpdateDto,
            HttpServletRequest request) {
        DiscountResponse updatedDiscount = discountService.updateDiscount(id, discountUpdateDto);
        CustomApiResponse<DiscountResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Discount updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedDiscount
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("discount/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a discount",
            description = "This endpoint allows administrators to delete a discount. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<DiscountResponse>> deleteDiscount(
            @PathVariable UUID id,
            HttpServletRequest request) {
        DiscountResponse deletedDiscount = discountService.deleteDiscount(id);
        CustomApiResponse<DiscountResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Discount deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedDiscount
        );
        return ResponseEntity.ok(response);
    }
}