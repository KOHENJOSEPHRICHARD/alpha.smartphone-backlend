package com.alphasmartphone.controller;

import com.alphasmartphone.dto.ApiResponse;
import com.alphasmartphone.dto.InquiryDTO;
import com.alphasmartphone.model.Inquiry;
import com.alphasmartphone.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class InquiryController {
    
    private final InquiryService inquiryService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<InquiryDTO>>> getAllInquiries() {
        List<InquiryDTO> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(ApiResponse.success("Inquiries retrieved successfully", inquiries));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InquiryDTO>> getInquiryById(@PathVariable Long id) {
        InquiryDTO inquiry = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(ApiResponse.success("Inquiry retrieved successfully", inquiry));
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<InquiryDTO>>> getInquiriesByStatus(@PathVariable Inquiry.InquiryStatus status) {
        List<InquiryDTO> inquiries = inquiryService.getInquiriesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Inquiries retrieved successfully", inquiries));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<InquiryDTO>> createInquiry(@Valid @RequestBody InquiryDTO inquiryDTO) {
        InquiryDTO createdInquiry = inquiryService.createInquiry(inquiryDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inquiry submitted successfully", createdInquiry));
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InquiryDTO>> updateInquiryStatus(
            @PathVariable Long id,
            @RequestParam Inquiry.InquiryStatus status,
            @RequestParam(required = false) String adminNotes) {
        InquiryDTO updatedInquiry = inquiryService.updateInquiryStatus(id, status, adminNotes);
        return ResponseEntity.ok(ApiResponse.success("Inquiry status updated successfully", updatedInquiry));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok(ApiResponse.success("Inquiry deleted successfully", null));
    }
}
