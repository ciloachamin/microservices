package com.espeshop.catalog.services;

import com.espeshop.catalog.model.dtos.CategoryRequest;
import com.espeshop.catalog.model.dtos.CategoryResponse;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.dao.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .slug(categoryRequest.getName().toLowerCase().replace(" ", "-"))
                .parentCategoryId(UUID.fromString(categoryRequest.getParentCategoryId()))
                .disabledReason(categoryRequest.getDisabledReason())
                .deleted(categoryRequest.isDeleted())
                .createdAt(categoryRequest.getCreatedAt())
                .createdUser(categoryRequest.getCreatedUser())
                .updatedAt(categoryRequest.getUpdatedAt())
                .updatedUser(categoryRequest.getUpdatedUser())
                .description(categoryRequest.getDescription())
                .image(categoryRequest.getImage())
                .enabled(categoryRequest.getEnabled())
                .build();
        return categoryRepository.save(category);
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        log.info("Retrieving all categories with pageable: {}", pageable);
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update category properties from categoryRequest
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setImage(categoryRequest.getImage());
        category.setEnabled(categoryRequest.getEnabled());

        categoryRepository.save(category);
        return mapToCategoryResponse(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .parentCategoryId(category.getParentCategoryId())
                .slug(category.getSlug())
                .disabledReason(category.getDisabledReason())
                .deleted(category.isDeleted())
                .createdAt(category.getCreatedAt())
                .createdUser(category.getCreatedUser())
                .updatedAt(category.getUpdatedAt())
                .updatedUser(category.getUpdatedUser())
                .description(category.getDescription())
                .enabled(category.isEnabled())
                .image(category.getImage())
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .enabled(category.isEnabled())
                .build();
    }
}
