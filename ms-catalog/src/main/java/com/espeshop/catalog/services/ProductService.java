package com.espeshop.catalog.services;

import com.espeshop.catalog.model.dto.ProductRequest;
import com.espeshop.catalog.model.dto.ProductResponse;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest) {
        var product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .stock(productRequest.getStock())
                .build();
        productRepository.save(product);
        log.info("Creating product: {}", productRequest);
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
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
