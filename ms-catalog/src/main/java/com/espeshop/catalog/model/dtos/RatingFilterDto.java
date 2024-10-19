package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingFilterDto {
    private OffsetDateTime dateBegin;
    private OffsetDateTime dateEnd;
    private Integer minRating;
    private Integer maxRating;
    private Integer rating;
    private Boolean deleted;
    private Boolean enabled;
    private UUID productId;
    private UUID userId;



    public boolean isEmpty() {
        return (rating == null) &&
                minRating == null &&
                maxRating == null &&
                productId == null &&
                dateBegin == null &&
                dateEnd == null &&
                deleted == null &&
                enabled == null &&
                userId == null
                ;
    }
}
