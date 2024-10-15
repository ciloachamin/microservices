package com.espeshop.catalog.services;

import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.FilterProductDto;
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


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .stock(productRequest.getStock())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);  // Convierte el producto a ProductResponse antes de devolverlo
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable, FilterProductDto filters) {
        log.info("Pageable: {}", pageable);
        log.info("Filters: {}", filters);

        Page<Product> products;

        // Verificar si el objeto filters es nulo o está vacío
        if (filters == null || filters.isEmpty()) {
            // Si no hay filtros, devolver todos los productos
            products = productRepository.findAll(pageable);
        } else {
            // Si hay algún filtro, llamar al método con filtros
            products = productRepository.findAllProducts(pageable, filters);
        }

        // Mapear cada Product a ProductResponse
        return products.map(this::mapToProductResponse);
    }



    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","id",id));
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

         product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .stock(productRequest.getStock())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);  // Convierte el producto a ProductResponse antes de devolverlo
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
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
