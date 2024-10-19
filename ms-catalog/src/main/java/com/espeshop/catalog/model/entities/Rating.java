package com.espeshop.catalog.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column
    private OffsetDateTime publishDate;

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

    // Relación con la entidad Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product; // Relación con la entidad Product

//    // Relación con la entidad User
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", insertable = false, updatable = false)
//    private User user; // Relación con la entidad Use
}
