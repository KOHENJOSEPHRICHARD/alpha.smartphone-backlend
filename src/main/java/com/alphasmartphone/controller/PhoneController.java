package com.alphasmartphone.controller;

import com.alphasmartphone.dto.ApiResponse;
import com.alphasmartphone.dto.PhoneDTO;
import com.alphasmartphone.model.Phone;
import com.alphasmartphone.service.PhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhoneController {
    
    private final PhoneService phoneService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> getAllPhones() {
        List<PhoneDTO> phones = phoneService.getAvailablePhones();
        return ResponseEntity.ok(ApiResponse.success("Phones retrieved successfully", phones));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhoneDTO>> getPhoneById(@PathVariable Long id) {
        PhoneDTO phone = phoneService.getPhoneById(id);
        return ResponseEntity.ok(ApiResponse.success("Phone retrieved successfully", phone));
    }
    
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> getFeaturedPhones() {
        List<PhoneDTO> phones = phoneService.getFeaturedPhones();
        return ResponseEntity.ok(ApiResponse.success("Featured phones retrieved successfully", phones));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> searchPhones(@RequestParam String keyword) {
        List<PhoneDTO> phones = phoneService.searchPhones(keyword);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", phones));
    }
    
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> filterPhones(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Phone.PhoneCondition condition) {
        List<PhoneDTO> phones = phoneService.filterPhones(brand, condition);
        return ResponseEntity.ok(ApiResponse.success("Filtered phones retrieved successfully", phones));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PhoneDTO>> createPhone(@Valid @RequestBody PhoneDTO phoneDTO) {
        PhoneDTO createdPhone = phoneService.createPhone(phoneDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Phone created successfully", createdPhone));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PhoneDTO>> updatePhone(
            @PathVariable Long id,
            @Valid @RequestBody PhoneDTO phoneDTO) {
        PhoneDTO updatedPhone = phoneService.updatePhone(id, phoneDTO);
        return ResponseEntity.ok(ApiResponse.success("Phone updated successfully", updatedPhone));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.ok(ApiResponse.success("Phone deleted successfully", null));
    }
}
