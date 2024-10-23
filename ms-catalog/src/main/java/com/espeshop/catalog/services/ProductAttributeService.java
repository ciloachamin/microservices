package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.AttributeRepository;
import com.espeshop.catalog.dao.repositories.ProductAttributeRepository;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Attribute;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.model.entities.ProductAttribute;
import com.espeshop.catalog.model.keys.ProductAttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    public ProductAttributeResponse createProductAttribute(ProductAttributeRequest productAttributeRequest) {
        Product product = productRepository.findById(productAttributeRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productAttributeRequest.getProductId()));

        Attribute attribute = attributeRepository.findById(productAttributeRequest.getAttributeId())
                .orElseThrow(() -> new ResourceNotFoundException("Attribute", "id", productAttributeRequest.getAttributeId()));

        ProductAttributeKey key = new ProductAttributeKey(product.getId(), attribute.getId());
        ProductAttribute productAttribute = ProductAttribute.builder()
                .id(key)
                .product(product)
                .attribute(attribute)
                .value(productAttributeRequest.getValue())
                .build();

        // Save the productAttribute to the repository
        productAttributeRepository.save(productAttribute);

        return mapToProductAttributeResponse(productAttribute);
    }

    public List<ProductAttributeResponse> getAllProductsAttributes(ProductAttributeFilterDto filters) {
        List<ProductAttribute> productsAttributes = filters == null || filters.isEmpty()
                ? productAttributeRepository.findAll()
                : productAttributeRepository.findAllProductsAttributes(filters);

        return productsAttributes.stream()
                .map(this::mapToProductAttributeResponse)
                .collect(Collectors.toList());
    }

    public ProductAttribute getProductAttributeById(ProductAttributeKey id) {
        return productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute", "id", id));
    }

    public ProductAttributeResponse updateProductAttribute(ProductAttributeKey id, ProductAttributeUpdateDto productAttributeUpdateDto) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute", "id", id));

        productAttribute.setValue(productAttributeUpdateDto.getValue());
        productAttributeRepository.save(productAttribute);

        return mapToProductAttributeResponse(productAttribute);
    }

    public ProductAttributeResponse deleteProductAttribute(ProductAttributeKey id) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAttribute", "id", id));

        // Delete the ProductAttribute from the repository
        productAttributeRepository.deleteById(id);

        return mapToProductAttributeResponse(productAttribute);
    }

    private ProductAttributeResponse mapToProductAttributeResponse(ProductAttribute productAttribute) {
        return ProductAttributeResponse.builder()
                .id(productAttribute.getId())
                .value(productAttribute.getValue())
                .build();
    }
}
