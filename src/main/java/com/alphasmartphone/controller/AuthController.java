package com.alphasmartphone.controller;

import com.alphasmartphone.dto.ApiResponse;
import com.alphasmartphone.dto.AuthRequest;
import com.alphasmartphone.dto.AuthResponse;
import com.alphasmartphone.model.Admin;
import com.alphasmartphone.security.JwtTokenProvider;
import com.alphasmartphone.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        Admin admin = adminService.getAdminByUsername(authRequest.getUsername());
        adminService.updateLastLogin(authRequest.getUsername());
        
        AuthResponse authResponse = new AuthResponse(
                jwt,
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getFullName(),
                admin.getRole().name()
        );
        
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }
    
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7);
            boolean isValid = jwtTokenProvider.validateToken(jwt);
            return ResponseEntity.ok(ApiResponse.success("Token validation result", isValid));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.success("Token validation result", false));
        }
    }
}
