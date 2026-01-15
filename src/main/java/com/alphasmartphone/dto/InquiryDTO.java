package com.alphasmartphone.dto;

import com.alphasmartphone.model.Inquiry;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    private String phoneNumber;
    
    private Long phoneId;
    private String phoneName;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    private Inquiry.InquiryStatus status;
    private String adminNotes;
    
    private LocalDateTime createdAt;
}
