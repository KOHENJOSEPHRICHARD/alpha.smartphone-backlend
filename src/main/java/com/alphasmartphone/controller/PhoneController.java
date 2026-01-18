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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhoneController {

    private final PhoneService phoneService;

    /* ===================== GET ===================== */

    @GetMapping
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> getAllPhones() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Phones retrieved successfully",
                        phoneService.getAvailablePhones()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhoneDTO>> getPhoneById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Phone retrieved successfully",
                        phoneService.getPhoneById(id)
                )
        );
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> getFeaturedPhones() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Featured phones retrieved successfully",
                        phoneService.getFeaturedPhones()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> searchPhones(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Search results retrieved successfully",
                        phoneService.searchPhones(keyword)
                )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<PhoneDTO>>> filterPhones(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Phone.PhoneCondition condition
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Filtered phones retrieved successfully",
                        phoneService.filterPhones(brand, condition)
                )
        );
    }

    /* ===================== CREATE ===================== */

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createPhone(
            @Valid @RequestBody PhoneDTO phoneDTO,
            BindingResult bindingResult
    ) {

        // 🔴 HANDLE VALIDATION MANUALLY (NO MORE SILENT 400)
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Validation failed", errors));
        }

        PhoneDTO createdPhone = phoneService.createPhone(phoneDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Phone created successfully", createdPhone));
    }

    /* ===================== UPDATE ===================== */

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updatePhone(
            @PathVariable Long id,
            @Valid @RequestBody PhoneDTO phoneDTO,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Validation failed", errors));
        }

        PhoneDTO updatedPhone = phoneService.updatePhone(id, phoneDTO);

        return ResponseEntity.ok(
                ApiResponse.success("Phone updated successfully", updatedPhone)
        );
    }

    /* ===================== DELETE ===================== */

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.ok(
                ApiResponse.success("Phone deleted successfully", null)
        );
    }
}
