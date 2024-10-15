package com.espeshop.catalog.services;

import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.CategoryRequest;
import com.espeshop.catalog.model.dtos.CategoryResponse;
import com.espeshop.catalog.model.dtos.FilterCategoryDto;
import com.espeshop.catalog.model.dtos.UpdateCategoryDto;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.dao.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
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
                .parentCategoryId(categoryRequest.getParentCategoryId())
                .createdAt(categoryRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);

    }

    public List<CategoryResponse> getAllCategories(FilterCategoryDto filters) {
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

    public CategoryResponse updateCategory(UUID id, UpdateCategoryDto updateCategoryDto) {
        Category category = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category.setName(updateCategoryDto.getName());
        category.setSlug(updateCategoryDto.getName().toLowerCase().replace(" ", "-"));
        category.setDescription(updateCategoryDto.getDescription());
        category.setImage(updateCategoryDto.getImage());
        category.setParentCategoryId(UUID.fromString(updateCategoryDto.getParentCategoryId()));
        category.setUpdatedAt(updateCategoryDto.getUpdatedAt());
        category.setUpdatedUser("MARIA PEREZ");
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
                .parentCategoryId(UUID.fromString(String.valueOf(category.getParentCategoryId())))
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
                .description(category.getDescription())
                .image(category.getImage())
                .build();
    }
}
