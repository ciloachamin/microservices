package com.espeshop.catalog.services;

import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.CategoryRequest;
import com.espeshop.catalog.model.dtos.CategoryResponse;
import com.espeshop.catalog.model.dtos.CategoryFilterDto;
import com.espeshop.catalog.model.dtos.CategoryUpdateDto;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.dao.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .image(categoryRequest.getImage())
                .slug(categoryRequest.getName().toLowerCase().replace(" ", "-"))
                .createdAt(categoryRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        // Si hay un ID de categoría padre, buscar y establecer la categoría padre
        if (categoryRequest.getParentCategoryId() != null) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(categoryRequest.getParentCategoryId());
            parentCategoryOptional.ifPresent(category::setParentCategory);
        }

        if (categoryRequest.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "parentCategoryId", categoryRequest.getParentCategoryId()));
            category.setParentCategory(parentCategory);
        }
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);

    }

    public List<CategoryResponse> getAllCategories(CategoryFilterDto filters) {
//        log.info("Filters: {}", filters);
        List<Category> categories;
        if (filters == null || filters.isEmpty()) {
            categories = categoryRepository.findAll();
        } else {
            categories = categoryRepository.findAllCategories(filters);
        }
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public CategoryResponse updateCategory(UUID id, CategoryUpdateDto categoryUpdateDto) {
        Category category = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category.setName(categoryUpdateDto.getName());
        category.setSlug(categoryUpdateDto.getName().toLowerCase().replace(" ", "-"));
        category.setDescription(categoryUpdateDto.getDescription());
        category.setImage(categoryUpdateDto.getImage());
        category.setUpdatedAt(categoryUpdateDto.getUpdatedAt());
        category.setUpdatedUser("MARIA PEREZ");

        if (categoryUpdateDto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(UUID.fromString(categoryUpdateDto.getParentCategoryId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "parentCategoryId", categoryUpdateDto.getParentCategoryId()));
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }
        categoryRepository.save(category);
        return mapToCategoryResponse(category);
    }

    public CategoryResponse deleteCategory(UUID id) {
        Category category = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category.setDeleted(true);
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .slug(category.getSlug())
                .disabledReason(category.getDisabledReason())
                .deleted(category.getDeleted())
                .createdAt(category.getCreatedAt())
                .createdUser(category.getCreatedUser())
                .updatedAt(category.getUpdatedAt())
                .updatedUser(category.getUpdatedUser())
                .description(category.getDescription())
                .enabled(category.getEnabled())
                .image(category.getImage())
                .name(category.getName())
                .build();
    }
}
