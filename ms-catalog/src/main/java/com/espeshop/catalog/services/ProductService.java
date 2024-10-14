package com.espeshop.catalog.services;

import com.espeshop.catalog.model.dtos.ProductFiltersDto;
import com.espeshop.catalog.model.dtos.ProductRequest;
import com.espeshop.catalog.model.dtos.ProductResponse;
import com.espeshop.catalog.model.entities.Product;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;


    public Product createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .stock(productRequest.getStock())
                .build();
        return  productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable, ProductFiltersDto filters) {
        log.info("Pageable: {}", pageable);
        log.info("Filters: {}", filters);

        // Verificar si el objeto filters es nulo o está vacío
        if (filters == null || filters.isEmpty()) {
            // Si no hay filtros, devolver todos los productos
            return productRepository.findAll(pageable);
        }

        // Si hay algún filtro, llamar al método con filtros
        return productRepository.findAllProducts(pageable, filters);
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

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        // Update product properties from productRequest
        productRepository.save(product);
        return new ProductResponse(); // Convert Product entity to ProductResponse
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
