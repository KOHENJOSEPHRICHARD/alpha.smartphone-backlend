package com.alphasmartphone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "phone_id", nullable = false)
    private Phone phone;
    
    private String customerName;
    private String customerEmail;
    private Integer rating; // 1-5
    private String title;
    private String comment;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    private Boolean isVerifiedPurchase = false;
}
