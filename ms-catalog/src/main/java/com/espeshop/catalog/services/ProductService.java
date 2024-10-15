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
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .stock(productRequest.getStock())
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
        product.setName(updateProductDto.getName());
        product.setSlug(updateProductDto.getName().toLowerCase().replace(" ", "-"));
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
                .stock(product.getStock())
                .skuCode(product.getSkuCode())
                .description(product.getDescription())
                .price(product.getPrice().doubleValue())
                .name(product.getName())
                .build();
    }

}
