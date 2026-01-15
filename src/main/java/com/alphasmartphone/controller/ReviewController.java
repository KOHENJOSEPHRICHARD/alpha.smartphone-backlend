package com.alphasmartphone.controller;

import com.alphasmartphone.model.Review;
import com.alphasmartphone.service.ReviewService;
import com.alphasmartphone.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping("/{phoneId}")
    public ResponseEntity<ApiResponse<Review>> createReview(
            @PathVariable Long phoneId,
            @RequestBody Review review) {
        Review createdReview = reviewService.createReview(phoneId, review);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Review created successfully", createdReview));
    }
    
    @GetMapping("/{phoneId}")
    public ResponseEntity<ApiResponse<List<Review>>> getPhoneReviews(@PathVariable Long phoneId) {
        List<Review> reviews = reviewService.getPhoneReviews(phoneId);
        return ResponseEntity.ok(ApiResponse.success("Reviews retrieved successfully", reviews));
    }
    
    @GetMapping("/{phoneId}/rating")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(@PathVariable Long phoneId) {
        Double rating = reviewService.getAverageRating(phoneId);
        return ResponseEntity.ok(ApiResponse.success("Average rating retrieved", rating));
    }
}
