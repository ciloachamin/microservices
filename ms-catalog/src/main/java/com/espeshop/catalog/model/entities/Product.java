package com.espeshop.catalog.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String skuCode;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private Integer stock;

    @Column(columnDefinition = "BOOLEAN DEFAULT NULL")
    private Boolean enabled;

    @Column(columnDefinition = "TEXT")
    private String disabledReason;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted = false;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private String createdUser;

    @Column
    private OffsetDateTime updatedAt;

    @Column
    private String updatedUser;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
