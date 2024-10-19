package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.services.ProductService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new product",
            description = "This endpoint allows administrators to create a new product. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest productRequest , HttpServletRequest request) {
        ProductResponse createdProduct = productService.createProduct(productRequest);
        CustomApiResponse<ProductResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Product created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdProduct
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all products",
            description = "Retrieve a list of all products. You can optionally filter by name.",
            parameters = {
                    @Parameter(name = "page", required = true),
                    @Parameter(name = "size", required = true),
                    @Parameter(name = "name", required = false),
                    @Parameter(name = "code", required = false),
                    @Parameter(name = "barcode", required = false),
                    @Parameter(name = "stock", required = false),
                    @Parameter(name = "brand", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
                    @Parameter(name = "dateBegin", required = false),
                    @Parameter(name = "dateEnd", required = false),
                    @Parameter(name = "priceMin", required = false),
                    @Parameter(name = "priceMax", required = false),
                    @Parameter(name = "stockMin", required = false),
                    @Parameter(name = "stockMax", required = false),
                    @Parameter(name = "categoryId", required = false),
                    @Parameter(name = "companyId", required = false),
                    @Parameter(name = "userId", required = false),
            }
    )
    public ResponseEntity<CustomApiResponse<Page<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) OffsetDateTime dateBegin,
            @RequestParam(required = false) OffsetDateTime dateEnd,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) Integer stockMin,
            @RequestParam(required = false) Integer stockMax,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID companyId,
            @RequestParam(required = false) UUID userId,
            HttpServletRequest request
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        ProductFilterDto filters = new ProductFilterDto(
                dateBegin,
                dateEnd,
                name,
                code,
                stock,
                brand,
                barcode,
                deleted,
                enabled,
                priceMin,
                priceMax,
                stockMin,
                stockMax,
                categoryId,
                companyId,
                userId
        );
        Page<ProductResponse> products = productService.getAllProducts(pageable, filters);
        CustomApiResponse<Page<ProductResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Products retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                products
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing product",
            description = "This endpoint allows administrators to update an existing product. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductResponse>> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductUpdateDto productUpdateDto,
            HttpServletRequest request
    ) {
        ProductResponse updatedProduct = productService.updateProduct(id, productUpdateDto);
        CustomApiResponse<ProductResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Product updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedProduct
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a product",
            description = "This endpoint allows administrators to delete a product. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductResponse>> deleteProduct(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ProductResponse deletedProduct = productService.deleteProduct(id);
        CustomApiResponse<ProductResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Product deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedProduct
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get product by ID",
            description = "Retrieve a product by its ID."
    )
    public ResponseEntity<CustomApiResponse<ProductResponse>> getProductById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ProductResponse productResponse = productService.getProductByIds(id);
        Set<ImageResponse> images = productResponse.getImages().stream()
                .map(image -> ImageResponse.builder()
                        .id(image.getId())
                        .imageUrl(image.getImageUrl())
                        .createdAt(image.getCreatedAt())
                        .createdUser(image.getCreatedUser())
                        .build())
                .collect(Collectors.toSet());

        CustomApiResponse<ProductResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Product retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                productResponse
        );
        return ResponseEntity.ok(response);
    }
}