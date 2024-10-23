package com.espeshop.catalog.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeKey implements Serializable {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "attribute_id")
    private UUID attributeId;

    // Debes sobrescribir equals y hashCode para que las comparaciones funcionen correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttributeKey that = (ProductAttributeKey) o;

        if (!productId.equals(that.productId)) return false;
        return attributeId.equals(that.attributeId);
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + attributeId.hashCode();
        return result;
    }
}
