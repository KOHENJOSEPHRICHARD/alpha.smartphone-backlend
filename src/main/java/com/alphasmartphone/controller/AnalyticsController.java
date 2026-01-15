package com.alphasmartphone.controller;

import com.alphasmartphone.dto.AnalyticsDTO;
import com.alphasmartphone.dto.ApiResponse;
import com.alphasmartphone.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @PostMapping("/track")
    public ResponseEntity<ApiResponse<String>> trackEvent(
            @RequestParam Long phoneId,
            @RequestParam String eventType,
            HttpServletRequest request) {
        
        String userAgent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        
        analyticsService.trackEvent(phoneId, eventType, userAgent, ipAddress);
        return ResponseEntity.ok(ApiResponse.success("Event tracked successfully", "OK"));
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AnalyticsDTO>> getDashboardAnalytics() {
        AnalyticsDTO analytics = analyticsService.getDashboardAnalytics();
        return ResponseEntity.ok(ApiResponse.success("Dashboard analytics retrieved", analytics));
    }
    
    @GetMapping("/top-products")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTopPerformingProducts() {
        Map<String, Object> topProducts = analyticsService.getTopPerformingProducts();
        return ResponseEntity.ok(ApiResponse.success("Top products retrieved", topProducts));
    }
}
