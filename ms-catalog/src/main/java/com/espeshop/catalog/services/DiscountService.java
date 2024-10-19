package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.CategoryRepository;
import com.espeshop.catalog.dao.repositories.DiscountRepository;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.DiscountFilterDto;
import com.espeshop.catalog.model.dtos.DiscountRequest;
import com.espeshop.catalog.model.dtos.DiscountResponse;
import com.espeshop.catalog.model.dtos.DiscountUpdateDto;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.model.entities.Discount;
import com.espeshop.catalog.model.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        Discount discount = Discount.builder()
                .name(discountRequest.getName())
                .description(discountRequest.getDescription())
                .discount(discountRequest.getDiscount())
                .discountType(discountRequest.getDiscountType())
                .startDate(discountRequest.getStartDate())
                .endDate(discountRequest.getEndDate())
                .createdAt(discountRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();

        // Relación con el producto
        if (discountRequest.getProductId() != null) {
            Product product = productRepository.findById(discountRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", discountRequest.getProductId()));
            discount.setProduct(product);
        }

        // Relación con la categoría
        if (discountRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(discountRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", discountRequest.getCategoryId()));
            discount.setCategory(category);
        }
        Discount savedDiscount = discountRepository.save(discount);
        return mapToDiscountResponse(savedDiscount);

    }

    public List<DiscountResponse> getAllDiscounts(DiscountFilterDto filters) {
//        log.info("Filters: {}", filters);
        List<Discount> discounts;
        if (filters == null || filters.isEmpty()) {
            discounts = discountRepository.findAll();
        } else {
            discounts = discountRepository.findAllDiscounts(filters);
        }
        return discounts.stream()
                .map(this::mapToDiscountResponse)
                .collect(Collectors.toList());
    }

    public Discount getDiscountById(UUID id) {
        return discountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", id));
    }

    public DiscountResponse updateDiscount(UUID id, DiscountUpdateDto discountUpdateDto) {
        Discount discount = discountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", id));


        discount.setName(discountUpdateDto.getName());
        discount.setDiscount(discountUpdateDto.getDiscount());
        discount.setDiscountType(discountUpdateDto.getDiscountType());
        // Verificar si el producto se ha actualizado
        if (discountUpdateDto.getProductId() != null) {
            Product product = (Product) productRepository.findById(discountUpdateDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", discountUpdateDto.getProductId()));
            discount.setProduct(product);
        } else {
            discount.setProduct(null);  // Eliminar relación si no se especifica producto
        }

        // Verificar si la categoría se ha actualizado
        if (discountUpdateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(discountUpdateDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", discountUpdateDto.getCategoryId()));
            discount.setCategory(category);
        } else {
            discount.setCategory(null);  // Eliminar relación si no se especifica categoría
        }
        discount.setDescription(discountUpdateDto.getDescription());
        discount.setStartDate(discountUpdateDto.getStartDate());
        discount.setEndDate(discountUpdateDto.getEndDate());
        discount.setUpdatedAt(discountUpdateDto.getUpdatedAt());
        discount.setUpdatedUser("MARIA PEREZ");
        discountRepository.save(discount);
        return mapToDiscountResponse(discount);
    }

    public DiscountResponse deleteDiscount(UUID id) {
        Discount discount = discountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", id));
        discount.setDeleted(true);
        Discount savedDiscount = discountRepository.save(discount);
        return mapToDiscountResponse(savedDiscount);
    }

    private DiscountResponse mapToDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .productId(discount.getProduct() != null ? discount.getProduct().getId() : null)
                .categoryId(discount.getCategory() != null ? discount.getCategory().getId() : null)
                .discount(discount.getDiscount())
                .discountType(discount.getDiscountType())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .disabledReason(discount.getDisabledReason())
                .deleted(discount.getDeleted())
                .createdAt(discount.getCreatedAt())
                .createdUser(discount.getCreatedUser())
                .updatedAt(discount.getUpdatedAt())
                .updatedUser(discount.getUpdatedUser())
                .description(discount.getDescription())
                .enabled(discount.getEnabled())
                .name(discount.getName())
                .build();
    }
}
