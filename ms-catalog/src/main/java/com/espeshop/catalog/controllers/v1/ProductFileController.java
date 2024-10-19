package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.ProductFile;
import com.espeshop.catalog.services.ProductFileService;
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
@Tag(name = "product-file", description = "Operations related to File product")
public class ProductFileController {

    private final ProductFileService productFileService;

    @PostMapping("/product-file")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new productFile",
            description = "This endpoint allows administrators to create a new product file. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductFileResponse>> createProductFile(@RequestBody @Valid ProductFileRequest productFileRequest, HttpServletRequest request) {
        ProductFileResponse createdProductFile = productFileService.createProductFile(productFileRequest);
        CustomApiResponse<ProductFileResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "ProductFile created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdProductFile
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/product-file")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all productFiles",
            description = "Retrieve a list of all productFiles.",
            parameters = {
                    @Parameter(name = "productId", required = false),
                    @Parameter(name = "deleted", required = false),
                    @Parameter(name = "enabled", required = false),
            }
    )
    public ResponseEntity<CustomApiResponse<List<ProductFileResponse>>> getAllProductFiles(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) Boolean deleted,
            @RequestParam(required = false) Boolean enabled,
            HttpServletRequest request
    ) {
        ProductFileFilterDto filters = new ProductFileFilterDto(productId, deleted, enabled);
        List<ProductFileResponse> productFiles = productFileService.getAllProductFiles(filters);
        CustomApiResponse<List<ProductFileResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductFiles retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                productFiles
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product-file/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get productFile by ID",
            description = "Retrieve a productFile by its ID."
    )
    public ResponseEntity<CustomApiResponse<ProductFile>> getProductFileById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ProductFile productFileResponse = productFileService.getProductFileById(id);
        CustomApiResponse<ProductFile> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductFile retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                productFileResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("product-file/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing productFile",
            description = "This endpoint allows administrators to update an existing productFile. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductFileResponse>> updateProductFile(
            @PathVariable UUID id,
            @RequestBody @Valid ProductFileUpdateDto productFileUpdateDto,
            HttpServletRequest request) {
        ProductFileResponse updatedProductFile = productFileService.updateProductFile(id, productFileUpdateDto);
        CustomApiResponse<ProductFileResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductFile updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedProductFile
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("product-file/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a productFile",
            description = "This endpoint allows administrators to delete a productFile. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<ProductFileResponse>> deleteProductFile(
            @PathVariable UUID id,
            HttpServletRequest request) {
        ProductFileResponse deletedProductFile = productFileService.deleteProductFile(id);
        CustomApiResponse<ProductFileResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "ProductFile deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedProductFile
        );
        return ResponseEntity.ok(response);
    }
}