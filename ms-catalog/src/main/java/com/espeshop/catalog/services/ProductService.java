package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.*;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.dtos.ProductResponse;
import com.espeshop.catalog.model.entities.*;
import com.espeshop.catalog.model.keys.ProductAttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ProductFileRepository productFileRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final AttributeRepository attributeRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {
        ProductAttributeKey key = new ProductAttributeKey();
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productRequest.getCategoryId()));
        Product product = Product.builder()
                .name(productRequest.getName())
                .slug(productRequest.getName().toLowerCase().replace(" ", "-"))
                .barcode(productRequest.getBarcode())
                .code(productRequest.getCode())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .rating(productRequest.getRating())
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
        // Verificar si el conjunto de imágenes es nulo
        if (productRequest.getImageUrls() != null && !productRequest.getImageUrls().isEmpty()) {
            Set<Image> images = productRequest.getImageUrls().stream()
                    .map(url -> Image.builder()
                            .product(savedProduct)  // Asociar cada imagen al producto
                            .imageUrl(url)
                            .createdAt(productRequest.getCreatedAt())
                            .createdUser("JUAN LOPEZ")
                            .deleted(false)  // Establecer el valor por defecto
                            .build())
                    .collect(Collectors.toSet());

            // Guardar las imágenes
            product.setImages(images);
            imageRepository.saveAll(images);

        }

        // Verificar si el conjunto de imágenes es nulo
        if (productRequest.getImageUrls() != null && !productRequest.getProductFileUrls().isEmpty()) {
            Set<ProductFile> productFiles = productRequest.getProductFileUrls().stream()
                    .map(url -> ProductFile.builder()
                            .product(savedProduct)  // Asociar cada imagen al producto
                            .productFileUrl(url)
                            .createdAt(productRequest.getCreatedAt())
                            .createdUser("JUAN LOPEZ")
                            .deleted(false)  // Establecer el valor por defecto
                            .build())
                    .collect(Collectors.toSet());

            // Guardar las imágenes
            product.setProductFiles(productFiles);
            productFileRepository.saveAll(productFiles);

        }
        // 5. Verificar y guardar atributos del producto
        if (productRequest.getAttributes() != null && !productRequest.getAttributes().isEmpty()) {
            Set<ProductAttribute> productAttributes = productRequest.getAttributes().stream()
                    .map(attrReq -> {
                        // Buscar el atributo en la base de datos (si ya existe) o crearlo
                        Attribute attribute = attributeRepository.findByName(attrReq.getName())
                                .orElseGet(() -> {
                                    // Crear y guardar el nuevo atributo en la base de datos
                                    Attribute newAttribute = Attribute.builder()
                                            .name(attrReq.getName())
                                            .dataType(attrReq.getDataType())
                                            .build();
                                    return attributeRepository.save(newAttribute);  // Guardar el atributo para asignar un ID
                                });


                        key.setProductId(savedProduct.getId());
                        key.setAttributeId(attribute.getId());
                        ProductAttribute newProductAttribute =  ProductAttribute.builder()
                                .id(key)
                                .product(savedProduct)
                                .attribute(attribute)
                                .value(attrReq.getValue())
                                .build();
                         productAttributeRepository.save(newProductAttribute);
                        return newProductAttribute;
                    })
                    .collect(Collectors.toSet());
                product.setProductAttribute(productAttributes);
        }
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
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return mapToProductResponse(product);
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
        product.setRating(productUpdateDto.getRating());
        product.setBrand(productUpdateDto.getBrand());
        product.setDescription(productUpdateDto.getDescription());
        product.setUpdatedAt(productUpdateDto.getUpdatedAt());  // Actualiza con la fecha y hora actual
        product.setUpdatedUser("MARIA PEREZ");
        productRepository.save(product);

        // Actualizar imágenes
        if (productUpdateDto.getImageUrls() != null) {
            Set<Image> existingImages = product.getImages();
            Set<Image> newImages = productUpdateDto.getImageUrls().stream()
                    .map(url -> Image.builder()
                            .product(product)
                            .imageUrl(url)
                            .createdAt(productUpdateDto.getUpdatedAt())  // Actualiza la fecha
                            .createdUser("MARIA PEREZ")
                            .deleted(false)
                            .build())
                    .collect(Collectors.toSet());

            // Eliminar imágenes que no estén en los nuevos URLs
            existingImages.forEach(image -> {
                if (newImages.stream().noneMatch(newImage -> newImage.getImageUrl().equals(image.getImageUrl()))) {
                    image.setDeleted(true);
                    imageRepository.save(image);
                }
            });

            // Agregar solo las nuevas imágenes que no existan en el set existente
            newImages.forEach(newImage -> {
                if (existingImages.stream().noneMatch(image -> image.getImageUrl().equals(newImage.getImageUrl()))) {
                    imageRepository.save(newImage);
                }
            });

            product.setImages(newImages);
        } else {
            // Si las URLs son null, eliminar todas las imágenes existentes
            product.getImages().forEach(image -> {
                image.setDeleted(true);
                imageRepository.save(image);
            });
            product.setImages(new HashSet<>());  // Vacía el conjunto de imágenes
        }


        // Actualizar archivos de producto
        if (productUpdateDto.getProductFileUrls() != null) {
            Set<ProductFile> existingProductFiles = product.getProductFiles();
            Set<ProductFile> newProductFiles = productUpdateDto.getProductFileUrls().stream()
                    .map(url -> ProductFile.builder()
                            .product(product)
                            .productFileUrl(url)
                            .createdAt(productUpdateDto.getUpdatedAt())  // Actualiza la fecha
                            .createdUser("MARIA PEREZ")
                            .deleted(false)
                            .build())
                    .collect(Collectors.toSet());

            // Eliminar archivos que no estén en los nuevos URLs
            existingProductFiles.forEach(productFile -> {
                if (newProductFiles.stream().noneMatch(newFile -> newFile.getProductFileUrl().equals(productFile.getProductFileUrl()))) {
                    productFile.setDeleted(true);
                    productFileRepository.save(productFile);
                }
            });

            // Agregar solo los nuevos archivos que no existan en el set existente
            newProductFiles.forEach(newFile -> {
                if (existingProductFiles.stream().noneMatch(file -> file.getProductFileUrl().equals(newFile.getProductFileUrl()))) {
                    productFileRepository.save(newFile);
                }
            });

            product.setProductFiles(newProductFiles);
        }
        else {
            // Si las URLs son null, eliminar todas las imágenes existentes
            product.getProductFiles().forEach(productFile -> {
                productFile.setDeleted(true);
                productFileRepository.save(productFile);
            });
            product.setProductFiles(new HashSet<>());  // Vacía el conjunto de imágenes
        }

        // Actualizar atributos de producto
// Código para actualizar atributos del producto
        if (productUpdateDto.getAttributes() != null) {
            Set<ProductAttribute> existingAttributes = product.getProductAttribute();
            Set<ProductAttribute> newAttributes = productUpdateDto.getAttributes().stream()
                    .map(attrReq -> {
                        Attribute attribute = attributeRepository.findByName(attrReq.getName())
                                .orElseGet(() -> {
                                    Attribute newAttribute = Attribute.builder()
                                            .name(attrReq.getName())
                                            .dataType(attrReq.getDataType())
                                            .build();
                                    return attributeRepository.save(newAttribute);
                                });

                        // Crear nueva clave compuesta para cada ProductAttribute
                        ProductAttributeKey key = new ProductAttributeKey();
                        key.setProductId(product.getId());
                        key.setAttributeId(attribute.getId());

                        return ProductAttribute.builder()
                                .id(key)
                                .product(product)
                                .attribute(attribute)
                                .value(attrReq.getValue())
                                .build();
                    })
                    .collect(Collectors.toSet());

            // Eliminar atributos antiguos que no están en los nuevos
            Set<ProductAttributeKey> newAttributeKeys = newAttributes.stream()
                    .map(ProductAttribute::getId)
                    .collect(Collectors.toSet());

            // Eliminar atributos antiguos que no están en los nuevos
            existingAttributes.forEach(existingAttribute -> {
                if (!newAttributeKeys.contains(existingAttribute.getId())) {
                    // Llamar al método personalizado para eliminar
                    productAttributeRepository.deleteByProductIdAndAttributeId(
                            existingAttribute.getId().getProductId(),
                            existingAttribute.getId().getAttributeId()
                    );
                }
            });

            // Guardar los nuevos atributos después de la eliminación
            productAttributeRepository.saveAll(newAttributes);
            product.setProductAttribute(newAttributes);
        } else {
            Set<ProductAttribute> existingAttributes = product.getProductAttribute();
            existingAttributes.forEach(attribute -> {
                productAttributeRepository.deleteByProductIdAndAttributeId(
                        attribute.getId().getProductId(),
                        attribute.getId().getAttributeId()
                );
            });
            product.setProductAttribute(new HashSet<>());  // Vacía el conjunto de atributos
        }


        return mapToProductResponse(product);
    }


    public ProductResponse deleteProduct(UUID id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setDeleted(true);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product product) {
        // Filtrar imágenes que no están eliminadas
        Set<ImageResponse> images = product.getImages().stream()
                .filter(image -> !image.getDeleted()) // Filtrar imágenes no eliminadas
                .map(image -> ImageResponse.builder()
                        .id(image.getId())
                        .productId(product.getId()) // Asignar el ID del producto
                        .imageUrl(image.getImageUrl())
                        .createdAt(image.getCreatedAt())
                        .createdUser(image.getCreatedUser())
                        .deleted(image.getDeleted())  // Mapear el valor correcto de 'deleted'
                        .build())
                .collect(Collectors.toSet());

        // Filtrar archivos de producto que no están eliminados
        Set<ProductFileResponse> productFiles = product.getProductFiles().stream()
                .filter(productFile -> !productFile.getDeleted()) // Filtrar archivos no eliminados
                .map(productFile -> ProductFileResponse.builder()
                        .id(productFile.getId())
                        .productId(product.getId()) // Asignar el ID del producto
                        .productFileUrl(productFile.getProductFileUrl())
                        .createdAt(productFile.getCreatedAt())
                        .createdUser(productFile.getCreatedUser())
                        .deleted(productFile.getDeleted())  // Mapear el valor correcto de 'deleted'
                        .build())
                .collect(Collectors.toSet());

        Set<AttributeResponse> productAttributes = product.getProductAttribute().stream()
                .map(productAttribute -> AttributeResponse.builder()
                        .id(productAttribute.getAttribute().getId())
                        .name(productAttribute.getAttribute().getName())
                        .value(productAttribute.getValue())
                        .dataType(productAttribute.getAttribute().getDataType())
                        .build())
                .collect(Collectors.toSet());

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .userId(product.getUserId())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .companyId(product.getCompanyId())
                .slug(product.getSlug())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .rating(product.getRating())
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
                .images(!images.isEmpty() ? images : null)
                .productFiles(!productFiles.isEmpty() ? productFiles : null)
                .attributes(!productAttributes.isEmpty() ? productAttributes : null)
                .build();
    }



}
