package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeFilterDto {
    private String name;
    private String dataType;


    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (dataType == null || dataType.isEmpty())
                ;
    }
}
