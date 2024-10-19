package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.CategoryRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.dtos.ProductResponse;
import com.espeshop.catalog.model.entities.Category;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productRequest.getCategoryId()));
        Product product = Product.builder()
                .name(productRequest.getName())
                .slug(productRequest.getName().toLowerCase().replace(" ", "-"))
                .barcode(productRequest.getBarcode())
                .code(productRequest.getCode())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .brand(productRequest.getBrand())
                .description(productRequest.getDescription())
                .userId(productRequest.getUserId())
                .companyId(productRequest.getCompanyId())
                .category(category)
                .createdAt(productRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable, ProductFilterDto filters) {
        Page<Product> products;
        if (filters == null || filters.isEmpty()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findAllProducts(pageable, filters);
        }
        return products.map(this::mapToProductResponse);
    }

    public Product getProductById(UUID id) {
        return productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }
    public ProductResponse getProductByIds(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Convertir las imágenes a DTOs
        Set<ImageResponse> imageResponses = product.getImages().stream()
                .map(image -> ImageResponse.builder()
                        .id(image.getId())
                        .productId(product.getId())
                        .imageUrl(image.getImageUrl())
                        .enabled(image.getEnabled())
                        .disabledReason(image.getDisabledReason())
                        .deleted(image.getDeleted())
                        .createdAt(image.getCreatedAt())
                        .createdUser(image.getCreatedUser())
                        .updatedAt(image.getUpdatedAt())
                        .updatedUser(image.getUpdatedUser())
                        .build())
                .collect(Collectors.toSet());

        // Convertir la categoría a DTO
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .slug(product.getCategory().getSlug())
                .description(product.getCategory().getDescription())
                .image(product.getCategory().getImage())
                .enabled(product.getCategory().getEnabled())
                .disabledReason(product.getCategory().getDisabledReason())
                .deleted(product.getCategory().getDeleted())
                .createdAt(product.getCategory().getCreatedAt())
                .createdUser(product.getCategory().getCreatedUser())
                .updatedAt(product.getCategory().getUpdatedAt())
                .updatedUser(product.getCategory().getUpdatedUser())
                .build();

        return ProductResponse.builder()
                .id(product.getId())
                .userId(product.getUserId())
                .categoryId(product.getCategory().getId())
                .companyId(product.getCompanyId())
                .name(product.getName())
                .slug(product.getSlug())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .code(product.getCode())
                .barcode(product.getBarcode())
                .enabled(product.getEnabled())
                .disabledReason(product.getDisabledReason())
                .deleted(product.getDeleted())
                .createdAt(product.getCreatedAt())
                .createdUser(product.getCreatedUser())
                .updatedAt(product.getUpdatedAt())
                .updatedUser(product.getUpdatedUser())
                .images(imageResponses)  // Agregar las imágenes
                .build();
    }


    public ProductResponse updateProduct(UUID id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        Category category = categoryRepository.findById(productUpdateDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productUpdateDto.getCategoryId()));
        product.setUserId(productUpdateDto.getUserId());
        product.setCategory(category);
        product.setCompanyId(productUpdateDto.getCompanyId());
        product.setName(productUpdateDto.getName());
        product.setSlug(productUpdateDto.getName().toLowerCase().replace(" ", "-"));
        product.setCode(productUpdateDto.getCode());
        product.setPrice(productUpdateDto.getPrice());
        product.setStock(productUpdateDto.getStock());
        product.setBrand(productUpdateDto.getBrand());
        product.setDescription(productUpdateDto.getDescription());
        product.setUpdatedAt(productUpdateDto.getUpdatedAt());
        product.setUpdatedUser("MARIA PEREZ");
        productRepository.save(product);
        return mapToProductResponse(product);}

    public ProductResponse deleteProduct(UUID id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setDeleted(true);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .userId(product.getUserId())
                .categoryId(product.getCategory().getId())
                .companyId(product.getCompanyId())
                .slug(product.getSlug())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .brand(product.getBrand())
                .code(product.getCode())
                .barcode(product.getBarcode())
                .enabled(product.getEnabled())
                .disabledReason(product.getDisabledReason())
                .deleted(product.getDeleted())
                .createdAt(product.getCreatedAt())
                .createdUser(product.getCreatedUser())
                .updatedAt(product.getUpdatedAt())
                .updatedUser(product.getUpdatedUser())
                .build();
    }

}
