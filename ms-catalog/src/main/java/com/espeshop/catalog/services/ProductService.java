package com.espeshop.catalog.services;

import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.dtos.ProductResponse;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
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
                .categoryId(productRequest.getCategoryId())
                .createdAt(productRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable, FilterProductDto filters) {
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

    public ProductResponse updateProduct(UUID id, UpdateProductDto updateProductDto) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setUserId(updateProductDto.getUserId());
        product.setCategoryId(updateProductDto.getCategoryId());
        product.setCompanyId(updateProductDto.getCompanyId());
        product.setName(updateProductDto.getName());
        product.setSlug(updateProductDto.getName().toLowerCase().replace(" ", "-"));
        product.setCode(updateProductDto.getCode());
        product.setPrice(updateProductDto.getPrice());
        product.setStock(updateProductDto.getStock());
        product.setBrand(updateProductDto.getBrand());
        product.setDescription(updateProductDto.getDescription());
        product.setUpdatedAt(updateProductDto.getUpdatedAt());
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
                .categoryId(product.getCategoryId())
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
