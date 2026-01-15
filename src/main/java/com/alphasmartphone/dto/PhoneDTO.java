package com.alphasmartphone.dto;

import com.alphasmartphone.model.Phone;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    
    private Long id;
    
    @NotBlank(message = "Phone name is required")
    private String name;
    
    @NotBlank(message = "Brand is required")
    private String brand;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    private String description;
    
    @NotNull(message = "Condition is required")
    private Phone.PhoneCondition condition;
    
    private List<String> images;
    
    // Specifications
    private String displaySize;
    private String displayType;
    private String processor;
    private String ram;
    private String storage;
    private String battery;
    private String mainCamera;
    private String frontCamera;
    private String operatingSystem;
    private String network;
    private String simType;
    private String colors;
    private String weight;
    private String dimensions;
    
    // Additional fields
    private Boolean isFeatured;
    private Boolean isAvailable;
    private Integer viewCount;
    private Integer inquiryCount;
    private List<String> tags;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
