package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.ImageRepository;
import com.espeshop.catalog.dao.repositories.ProductRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.ImageRequest;
import com.espeshop.catalog.model.dtos.ImageResponse;
import com.espeshop.catalog.model.dtos.ImageFilterDto;
import com.espeshop.catalog.model.dtos.ImageUpdateDto;
import com.espeshop.catalog.model.entities.Image;
import com.espeshop.catalog.model.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ImageResponse createImage(ImageRequest imageRequest) {
        Product product = productRepository.findById(UUID.fromString(imageRequest.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", imageRequest.getProductId()));

        Image image = Image.builder()
                .imageUrl(imageRequest.getImageUrl())
                .product(product)
                .createdAt(imageRequest.getCreatedAt())
                .deleted(false)
                .createdUser("JUAN LOPEZ")
                .build();
        Image savedImage = imageRepository.save(image);
        return mapToImageResponse(savedImage);

    }

    public List<ImageResponse> getAllImages(ImageFilterDto filters) {
//        log.info("Filters: {}", filters);
        List<Image> images;
        if (filters == null || filters.isEmpty()) {
            images = imageRepository.findAll();
        } else {
            images = imageRepository.findAllImages(filters);
        }
        return images.stream()
                .map(this::mapToImageResponse)
                .collect(Collectors.toList());
    }

    public Image getImageById(UUID id) {
        return imageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
    }

    public ImageResponse updateImage(UUID id, ImageUpdateDto imageUpdateDto) {
        Image image = imageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));

        image.setImageUrl(imageUpdateDto.getProductId());
        if (imageUpdateDto.getProductId() != null) {
            Product product = productRepository.findById(UUID.fromString(imageUpdateDto.getProductId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", imageUpdateDto.getProductId()));
            image.setProduct(product);
        } else {
            image.setProduct(null);
        }
        image.setUpdatedAt(imageUpdateDto.getUpdatedAt());
        image.setUpdatedUser("MARIA PEREZ");
        imageRepository.save(image);
        return mapToImageResponse(image);
    }

    public ImageResponse deleteImage(UUID id) {
        Image image = imageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        image.setDeleted(true);
        Image savedImage = imageRepository.save(image);
        return mapToImageResponse(savedImage);
    }

    private ImageResponse mapToImageResponse(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .productId(image.getProduct() != null ? image.getProduct().getId() : null)  // devolver el ID del producto
                .disabledReason(image.getDisabledReason())
                .deleted(image.getDeleted())
                .createdAt(image.getCreatedAt())
                .createdUser(image.getCreatedUser())
                .updatedAt(image.getUpdatedAt())
                .updatedUser(image.getUpdatedUser())
                .enabled(image.getEnabled())
                .build();
    }
}
