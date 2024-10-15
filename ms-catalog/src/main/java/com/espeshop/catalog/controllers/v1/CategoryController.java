package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.services.CategoryService;
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
@Tag(name = "Category", description = "Operations related to categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new category",
            description = "This endpoint allows administrators to create a new category. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryRequest categoryRequest, HttpServletRequest request) {
        CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);
        CustomApiResponse<CategoryResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Category created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdCategory
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all categories.",
            parameters = {
                    @Parameter(name = "name", required = false),
                    @Parameter(name = "parentCategoryId", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
                    @Parameter(name = "userId", required = false)
            }
    )
    public ResponseEntity<CustomApiResponse<List<CategoryResponse>>> getAllCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID parentCategoryId,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) UUID userId,
            HttpServletRequest request
    ) {
        FilterCategoryDto filters = new FilterCategoryDto(name, parentCategoryId, deleted, enabled, userId);
        List<CategoryResponse> categories = categoryService.getAllCategories(filters);
        CustomApiResponse<List<CategoryResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Categories retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                categories
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get category by ID",
            description = "Retrieve a category by its ID."
    )
    public ResponseEntity<CustomApiResponse<Category>> getCategoryById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        Category categoryResponse = categoryService.getCategoryById(id);
        CustomApiResponse<Category> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Category retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                categoryResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing category",
            description = "This endpoint allows administrators to update an existing category. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<CategoryResponse>> updateCategory(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateCategoryDto updateCategoryDto,
            HttpServletRequest request) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, updateCategoryDto);
        CustomApiResponse<CategoryResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Category updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedCategory
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a category",
            description = "This endpoint allows administrators to delete a category. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<CategoryResponse>> deleteCategory(
            @PathVariable UUID id,
            HttpServletRequest request) {
        CategoryResponse deletedCategory = categoryService.deleteCategory(id);
        CustomApiResponse<CategoryResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Category deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedCategory
        );
        return ResponseEntity.ok(response);
    }
}