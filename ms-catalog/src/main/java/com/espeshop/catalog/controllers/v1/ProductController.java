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
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

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
                    @Parameter(name = "skuCode", required = false),
                    @Parameter(name = "stock", required = false),
                    @Parameter(name = "dateBegin", required = false),
                    @Parameter(name = "dateEnd", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
                    @Parameter(name = "userId", required = false)
            }
    )
    public ResponseEntity<CustomApiResponse<Page<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String skuCode,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) OffsetDateTime dateBegin,
            @RequestParam(required = false) OffsetDateTime dateEnd,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) UUID userId,
            HttpServletRequest request
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        FilterProductDto filters = new FilterProductDto(name, skuCode, stock, dateBegin, dateEnd, deleted, enabled, userId);
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
            @RequestBody @Valid UpdateProductDto updateProductDto,
            HttpServletRequest request
    ) {
        ProductResponse updatedProduct = productService.updateProduct(id,updateProductDto);
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
    public ResponseEntity<CustomApiResponse<Product>> getProductById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        Product productResponse = productService.getProductById(id);
        CustomApiResponse<Product> response = new CustomApiResponse<>(
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