package com.alphasmartphone.repository;

import com.alphasmartphone.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    
    List<Inquiry> findByStatus(Inquiry.InquiryStatus status);
    
    List<Inquiry> findByPhoneId(Long phoneId);
    
    List<Inquiry> findByEmail(String email);
}
