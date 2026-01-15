package com.alphasmartphone.controller;

import com.alphasmartphone.model.Wishlist;
import com.alphasmartphone.service.WishlistService;
import com.alphasmartphone.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class WishlistController {
    
    private final WishlistService wishlistService;
    
    @PostMapping("/{phoneId}")
    public ResponseEntity<ApiResponse<Wishlist>> addToWishlist(
            @PathVariable Long phoneId,
            @RequestParam String sessionId) {
        Wishlist wishlist = wishlistService.addToWishlist(phoneId, sessionId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Added to wishlist", wishlist));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Wishlist>>> getUserWishlist(@RequestParam String sessionId) {
        List<Wishlist> wishlist = wishlistService.getUserWishlist(sessionId);
        return ResponseEntity.ok(ApiResponse.success("Wishlist retrieved", wishlist));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable Long id) {
        wishlistService.removeFromWishlist(id);
        return ResponseEntity.ok(ApiResponse.success("Removed from wishlist", null));
    }
}
