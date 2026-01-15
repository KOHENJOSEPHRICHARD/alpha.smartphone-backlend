package com.alphasmartphone.controller;

import com.alphasmartphone.service.ComparisonService;
import com.alphasmartphone.dto.ApiResponse;
import com.alphasmartphone.dto.ComparisonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compare")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ComparisonController {
    
    private final ComparisonService comparisonService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ComparisonDTO>> comparePhones(@RequestBody List<Long> phoneIds) {
        ComparisonDTO comparison = comparisonService.comparePhones(phoneIds);
        return ResponseEntity.ok(ApiResponse.success("Phones compared successfully", comparison));
    }
}
