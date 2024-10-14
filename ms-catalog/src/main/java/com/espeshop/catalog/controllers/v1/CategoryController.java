package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.CategoryRequest;
import com.espeshop.catalog.model.dtos.CategoryResponse;
import com.espeshop.catalog.model.dtos.ProductFiltersDto;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Operations related to categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new category",
            description = "This endpoint allows administrators to create a new category. Only users with 'ROLE_ADMIN' can access this.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all categories.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            },
            parameters = {
                    @Parameter(name = "page", required = true),
                    @Parameter(name = "size", required = true),
                    @Parameter(name = "name", required = false),
                    @Parameter(name = "skuCode", required = false),
                    @Parameter(name = "stock", required = false),
                    @Parameter(name = "date_begin", required = false),
                    @Parameter(name = "date_end", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
                    @Parameter(name = "user_id", required = false)
            }
    )
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String skuCode,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) Long dateBegin,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) String deleted,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String userId
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        ProductFiltersDto filters = new ProductFiltersDto(name, skuCode, stock, dateBegin, dateEnd, deleted, enabled, userId);
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }

    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get category by ID",
            description = "Retrieve a category by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        Category categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing category",
            description = "This endpoint allows administrators to update an existing category. Only users with 'ROLE_ADMIN' can access this.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a category",
            description = "This endpoint allows administrators to delete a category. Only users with 'ROLE_ADMIN' can access this.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}