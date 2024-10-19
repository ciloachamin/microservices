package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.ProductAttribute;
import com.espeshop.catalog.services.ProductAttributeService;
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
@Tag(name = "ProductAttribute", description = "Operations related to productsAttributes")
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @PostMapping("/ProductAttribute")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new ProductAttribute",
            description = "This endpoint allows administrators to create a new ProductAttribute. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductAttributeResponse>> createProductAttribute(@RequestBody @Valid ProductAttributeRequest ProductAttributeRequest, HttpServletRequest request) {
        ProductAttributeResponse createdProductAttribute = productAttributeService.createProductAttribute(ProductAttributeRequest);
        CustomApiResponse<ProductAttributeResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "ProductAttribute created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdProductAttribute
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/productsAttributes")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all productsAttributes",
            description = "Retrieve a list of all productsAttributes.",
            parameters = {
                    @Parameter(name = "productId", required = false),
                    @Parameter(name = "attributeId", required = false),
            }
    )
    public ResponseEntity<CustomApiResponse<List<ProductAttributeResponse>>> getAllProductsAttributes(
            @RequestParam(required = false) UUID attributeId,
            @RequestParam(required = false) UUID productId,
            HttpServletRequest request
    ) {
        ProductAttributeFilterDto filters = new ProductAttributeFilterDto(attributeId,productId);
        List<ProductAttributeResponse> productsAttributes = productAttributeService.getAllProductsAttributes(filters);
        CustomApiResponse<List<ProductAttributeResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductsAttributes retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                productsAttributes
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ProductAttribute/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get ProductAttribute by ID",
            description = "Retrieve a ProductAttribute by its ID."
    )
    public ResponseEntity<CustomApiResponse<ProductAttribute>> getProductAttributeById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ProductAttribute ProductAttributeResponse = productAttributeService.getProductAttributeById(id);
        CustomApiResponse<ProductAttribute> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductAttribute retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                ProductAttributeResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("ProductAttribute/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing ProductAttribute",
            description = "This endpoint allows administrators to update an existing ProductAttribute. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductAttributeResponse>> updateProductAttribute(
            @PathVariable UUID id,
            @RequestBody @Valid ProductAttributeUpdateDto ProductAttributeUpdateDto,
            HttpServletRequest request) {
        ProductAttributeResponse updatedProductAttribute = productAttributeService.updateProductAttribute(id, ProductAttributeUpdateDto);
        CustomApiResponse<ProductAttributeResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductAttribute updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedProductAttribute
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("ProductAttribute/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a ProductAttribute",
            description = "This endpoint allows administrators to delete a ProductAttribute. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductAttributeResponse>> deleteProductAttribute(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ProductAttributeResponse deletedProductAttribute = productAttributeService.deleteProductAttribute(id);
        CustomApiResponse<ProductAttributeResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductAttribute deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedProductAttribute
        );
        return ResponseEntity.ok(response);
    }
}