    package com.alphasmartphone.service;

import com.alphasmartphone.exception.ResourceNotFoundException;
import com.alphasmartphone.model.Admin;
import com.alphasmartphone.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public Admin getAdminById(Long id) {
        return adminRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
    }
    
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with username: " + username));
    }
    
    public Admin createAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }
    
    public Admin updateAdmin(Long id, Admin adminDetails) {
        Admin admin = getAdminById(id);
        
        admin.setFullName(adminDetails.getFullName());
        admin.setEmail(adminDetails.getEmail());
        admin.setPhoneNumber(adminDetails.getPhoneNumber());
        admin.setRole(adminDetails.getRole());
        admin.setIsActive(adminDetails.getIsActive());
        
        return adminRepository.save(admin);
    }
    
    public void updateLastLogin(String username) {
        Admin admin = getAdminByUsername(username);
        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);
    }
    
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        adminRepository.delete(Objects.requireNonNull(admin));
    }
}
