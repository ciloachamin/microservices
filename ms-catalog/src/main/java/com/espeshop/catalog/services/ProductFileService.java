package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.ProductFileRepository;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.ProductFileFilterDto;
import com.espeshop.catalog.model.dtos.ProductFileRequest;
import com.espeshop.catalog.model.dtos.ProductFileResponse;
import com.espeshop.catalog.model.dtos.ProductFileUpdateDto;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.model.entities.ProductFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFileService {
    private final ProductFileRepository productFileRepository;
    private final ProductRepository productRepository;

    public ProductFileResponse createProductFile(ProductFileRequest productFileRequest) {
        Product product = productRepository.findById(UUID.fromString(productFileRequest.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productFileRequest.getProductId()));
        ProductFile productFile = ProductFile.builder()
                .productFileUrl(productFileRequest.getProductFileUrl())
                .product(product)
                .createdAt(productFileRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        ProductFile savedProductFile = productFileRepository.save(productFile);
        return mapToProductFileResponse(savedProductFile);

    }

    public List<ProductFileResponse> getAllProductFiles(ProductFileFilterDto filters) {
//        log.info("Filters: {}", filters);
        List<ProductFile> productFiles;
        if (filters == null || filters.isEmpty()) {
            productFiles = productFileRepository.findAll();
        } else {
            productFiles = productFileRepository.findAllProductFiles(filters);
        }
        return productFiles.stream()
                .map(this::mapToProductFileResponse)
                .collect(Collectors.toList());
    }

    public ProductFile getProductFileById(UUID id) {
        return productFileRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductFile", "id", id));
    }

    public ProductFileResponse updateProductFile(UUID id, ProductFileUpdateDto productFileUpdateDto) {
        ProductFile productFile = productFileRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductFile", "id", id));
        productFile.setProductFileUrl(productFileUpdateDto.getProductId());
        if (productFileUpdateDto.getProductId() != null) {
            Product product = productRepository.findById(UUID.fromString(productFileUpdateDto.getProductId()))
                    .orElseThrow(() -> new ResourceNotFoundException("ProductFile", "id", productFileUpdateDto.getProductId()));
            productFile.setProduct(product);
        } else {
            productFile.setProduct(null);
        }
        productFile.setUpdatedAt(productFileUpdateDto.getUpdatedAt());
        productFile.setUpdatedUser("MARIA PEREZ");
        productFileRepository.save(productFile);
        return mapToProductFileResponse(productFile);
    }

    public ProductFileResponse deleteProductFile(UUID id) {
        ProductFile productFile = productFileRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductFile", "id", id));
        productFile.setDeleted(true);
        ProductFile savedProductFile = productFileRepository.save(productFile);
        return mapToProductFileResponse(savedProductFile);
    }

    private ProductFileResponse mapToProductFileResponse(ProductFile productFile) {
        return ProductFileResponse.builder()
                .id(productFile.getId())
                .productFileUrl(productFile.getProductFileUrl())
                .productId(productFile.getProduct() != null ? productFile.getProduct().getId() : null)  // devolver el ID del producto
                .disabledReason(productFile.getDisabledReason())
                .deleted(productFile.getDeleted())
                .createdAt(productFile.getCreatedAt())
                .createdUser(productFile.getCreatedUser())
                .updatedAt(productFile.getUpdatedAt())
                .updatedUser(productFile.getUpdatedUser())
                .enabled(productFile.getEnabled())
                .build();
    }
}
