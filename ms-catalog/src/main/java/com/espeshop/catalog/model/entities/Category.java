package com.espeshop.catalog.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private UUID parentCategoryId;

    @Column(columnDefinition = "TEXT")
    private String image;

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
}
