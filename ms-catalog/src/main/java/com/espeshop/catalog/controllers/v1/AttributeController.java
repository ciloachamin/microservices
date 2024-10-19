package com.espeshop.catalog.controllers.v1;

import com.espeshop.catalog.model.dtos.*;
import com.espeshop.catalog.model.entities.Attribute;
import com.espeshop.catalog.services.AttributeService;
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
@Tag(name = "Attribute", description = "Operations related to Attributes")
public class AttributeController {

    private final AttributeService AttributeService;

    @PostMapping("/Attribute")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Create a new Attribute",
            description = "This endpoint allows administrators to create a new Attribute. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<AttributeResponse>> createAttribute(@RequestBody @Valid AttributeRequest AttributeRequest, HttpServletRequest request) {
        AttributeResponse createdAttribute = AttributeService.createAttribute(AttributeRequest);
        CustomApiResponse<AttributeResponse> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                "Attribute created successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                createdAttribute
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/Attributes")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get all Attributes",
            description = "Retrieve a list of all Attributes.",
            parameters = {
                    @Parameter(name = "name", required = false),
                    @Parameter(name = "dataType", required = false),
            }
    )
    public ResponseEntity<CustomApiResponse<List<AttributeResponse>>> getAllAttributes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dataType,
            HttpServletRequest request
    ) {
        AttributeFilterDto filters = new AttributeFilterDto(name,dataType);
        List<AttributeResponse> Attributes = AttributeService.getAllAttributes(filters);
        CustomApiResponse<List<AttributeResponse>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Attributes retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                Attributes
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Attribute/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get Attribute by ID",
            description = "Retrieve a Attribute by its ID."
    )
    public ResponseEntity<CustomApiResponse<Attribute>> getAttributeById(
            @PathVariable UUID id,
            HttpServletRequest request) {
        Attribute AttributeResponse = AttributeService.getAttributeById(id);
        CustomApiResponse<Attribute> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Attribute retrieved successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                AttributeResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("Attribute/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update an existing Attribute",
            description = "This endpoint allows administrators to update an existing Attribute. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<AttributeResponse>> updateAttribute(
            @PathVariable UUID id,
            @RequestBody @Valid AttributeUpdateDto AttributeUpdateDto,
            HttpServletRequest request) {
        AttributeResponse updatedAttribute = AttributeService.updateAttribute(id, AttributeUpdateDto);
        CustomApiResponse<AttributeResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Attribute updated successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                updatedAttribute
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("Attribute/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a Attribute",
            description = "This endpoint allows administrators to delete a Attribute. Only users with 'ROLE_ADMIN' can access this."
    )
    public ResponseEntity<CustomApiResponse<AttributeResponse>> deleteAttribute(
            @PathVariable UUID id,
            HttpServletRequest request) {
        AttributeResponse deletedAttribute = AttributeService.deleteAttribute(id);
        CustomApiResponse<AttributeResponse> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                true,
                "Attribute deleted successfully",
                request.getRequestURI(),
                LocalDateTime.now(),
                deletedAttribute
        );
        return ResponseEntity.ok(response);
    }
}