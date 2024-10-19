package com.espeshop.catalog.services;

import com.espeshop.catalog.dao.repositories.AttributeRepository;
import com.espeshop.catalog.exception.ResourceNotFoundException;
import com.espeshop.catalog.model.dtos.AttributeFilterDto;
import com.espeshop.catalog.model.dtos.AttributeRequest;
import com.espeshop.catalog.model.dtos.AttributeResponse;
import com.espeshop.catalog.model.dtos.AttributeUpdateDto;
import com.espeshop.catalog.model.entities.Attribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeService {
    private final AttributeRepository attributeRepository;

    public AttributeResponse createAttribute(AttributeRequest attributeRequest) {
        Attribute attribute = Attribute.builder()
                .name(attributeRequest.getName())
                .dataType(attributeRequest.getDataType())
                .build();
        Attribute savedAttribute = attributeRepository.save(attribute);
        return mapToAttributeResponse(savedAttribute);

    }

    public List<AttributeResponse> getAllAttributes(AttributeFilterDto filters) {
//        log.info("Filters: {}", filters);
        List<Attribute> attributes;
        if (filters == null || filters.isEmpty()) {
            attributes = attributeRepository.findAll();
        } else {
            attributes = attributeRepository.findAllAttributes(filters);
        }
        return attributes.stream()
                .map(this::mapToAttributeResponse)
                .collect(Collectors.toList());
    }

    public Attribute getAttributeById(UUID id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute", "id", id));
    }

    public AttributeResponse updateAttribute(UUID id, AttributeUpdateDto attributeUpdateDto) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute", "id", id));
        attribute.setName(attributeUpdateDto.getName());
        attribute.setDataType(attributeUpdateDto.getDataType());
        attributeRepository.save(attribute);
        return mapToAttributeResponse(attribute);
    }

    public AttributeResponse deleteAttribute(UUID id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute", "id", id));
        Attribute savedAttribute = attributeRepository.save(attribute);
        return mapToAttributeResponse(savedAttribute);
    }

    private AttributeResponse mapToAttributeResponse(Attribute aattribute) {
        return AttributeResponse.builder()
                .id(aattribute.getId())
                .name(aattribute.getName())
                .dataType(aattribute.getDataType())
                .build();


    }
}
