package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Image;
import com.espeshop.catalog.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Image", description = "Operations related to images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new image",
            description = "This endpoint allows administrators to create a new image. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ImageResponse>> createImage(@RequestBody @Valid ImageRequest imageRequest, HttpServletRequest request) {
        ImageResponse createdImage = imageService.createImage(imageRequest);
        CustomApiResponse<ImageResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Image created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdImage
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all images",
            description = "Retrieve a list of all images.",
            parameters = {
                    @Parameter(name = "productId", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
            }
    )
    public ResponseEntity<CustomApiResponse<List<ImageResponse>>> getAllImages(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            HttpServletRequest request
    ) {
        ImageFilterDto filters = new ImageFilterDto(productId, deleted, enabled);
        List<ImageResponse> images = imageService.getAllImages(filters);
        CustomApiResponse<List<ImageResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Images retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                images
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/image/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get image by ID",
            description = "Retrieve a image by its ID."
    )
    public ResponseEntity<CustomApiResponse<Image>> getImageById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        Image imageResponse = imageService.getImageById(id);
        CustomApiResponse<Image> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Image retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                imageResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("image/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing image",
            description = "This endpoint allows administrators to update an existing image. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ImageResponse>> updateImage(
            @PathVariable UUID id,
            @RequestBody @Valid ImageUpdateDto imageUpdateDto,
            HttpServletRequest request) {
        ImageResponse updatedImage = imageService.updateImage(id, imageUpdateDto);
        CustomApiResponse<ImageResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Image updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedImage
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("image/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a image",
            description = "This endpoint allows administrators to delete a image. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ImageResponse>> deleteImage(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ImageResponse deletedImage = imageService.deleteImage(id);
        CustomApiResponse<ImageResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Image deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedImage
        );
        return ResponseEntity.ok(response);
    }
}