package com.alphasmartphone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========================= BASIC INFO =========================

    @NotBlank(message = "Phone name is required")
    @Size(min = 2, max = 100, message = "Phone name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Brand is required")
    @Column(nullable = false, length = 100)
    private String brand;

    @NotBlank(message = "Model is required")
    @Column(nullable = false, length = 100)
    private String model;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Condition is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PhoneCondition condition;

    // ========================= IMAGES =========================

    @ElementCollection
    @CollectionTable(
            name = "phone_images",
            joinColumns = @JoinColumn(name = "phone_id")
    )
    @Column(
            name = "image_url",
            columnDefinition = "TEXT",
            nullable = false
    )
    private List<String> images = new ArrayList<>();

    // ========================= SPECIFICATIONS =========================

    @Column(name = "display_size", length = 50)
    private String displaySize;

    @Column(name = "display_type", length = 50)
    private String displayType;

    @Column(length = 100)
    private String processor;

    @Column(length = 50)
    private String ram;

    @Column(length = 50)
    private String storage;

    @Column(length = 50)
    private String battery;

    @Column(name = "main_camera", length = 100)
    private String mainCamera;

    @Column(name = "front_camera", length = 100)
    private String frontCamera;

    @Column(name = "operating_system", length = 50)
    private String operatingSystem;

    @Column(length = 50)
    private String network;

    @Column(name = "sim_type", length = 50)
    private String simType;

    @Column(length = 100)
    private String colors;

    @Column(length = 50)
    private String weight;

    @Column(length = 100)
    private String dimensions;

    // ========================= FLAGS & STATS =========================

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "inquiry_count", nullable = false)
    private Integer inquiryCount = 0;

    // ========================= TAGS =========================

    @ElementCollection
    @CollectionTable(
            name = "phone_tags",
            joinColumns = @JoinColumn(name = "phone_id")
    )
    @Column(name = "tag", length = 50)
    private List<String> tags = new ArrayList<>();

    // ========================= AUDIT =========================

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ========================= ENUM =========================

    public enum PhoneCondition {
        BRAND_NEW,
        LIKE_NEW,
        EXCELLENT,
        GOOD,
        FAIR,
        REFURBISHED
    }
}
