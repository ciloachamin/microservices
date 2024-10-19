package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.ProductAttributeRepository;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.ProductAttributeFilterDto;
import com.espeshop.catalog.model.dtos.ProductAttributeRequest;
import com.espeshop.catalog.model.dtos.ProductAttributeResponse;
import com.espeshop.catalog.model.dtos.ProductAttributeUpdateDto;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.model.entities.ProductAttribute;
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
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;

    public ProductAttributeResponse createProductAttribute(ProductAttributeRequest productAttributeRequest) {
        Product product = (Product) productRepository.findById(productAttributeRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productAttributeRequest.getProductId()));

        ProductAttribute productAttribute = ProductAttribute.builder()
                .product(product)
                .value(productAttributeRequest.getValue())
                .build();
        return mapToProductAttributeResponse(productAttribute);

    }

    public List<ProductAttributeResponse> getAllProductsAttributes(ProductAttributeFilterDto filters) {
//        log.info("Filters: {}", filters);
        List<ProductAttribute> productsAttributes;
        if (filters == null || filters.isEmpty()) {
            productsAttributes = productAttributeRepository.findAll();
        } else {
            productsAttributes = productAttributeRepository.findAllProductsAttributes(filters);
        }
        return productsAttributes.stream()
                .map(this::mapToProductAttributeResponse)
                .collect(Collectors.toList());
    }

    public ProductAttribute getProductAttributeById(UUID id) {
        return productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute", "id", id));
    }

    public ProductAttributeResponse updateProductAttribute(UUID id, ProductAttributeUpdateDto productAttributeUpdateDto) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute", "id", id));
        productAttribute.setValue(productAttributeUpdateDto.getValue());
        productAttributeRepository.save(productAttribute);
        return mapToProductAttributeResponse(productAttribute);
    }

    public ProductAttributeResponse deleteProductAttribute(UUID id) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute", "id", id));
        productAttributeRepository.save(productAttribute);
        return mapToProductAttributeResponse(productAttribute);
    }

    private ProductAttributeResponse mapToProductAttributeResponse(ProductAttribute productAttribute) {
        return ProductAttributeResponse.builder()
                .id(productAttribute.getId())
                .productId(productAttribute.getProduct())
                .value(productAttribute.getValue())
                .build();
    }
}
