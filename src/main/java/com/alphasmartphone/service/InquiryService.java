package com.alphasmartphone.service;

import com.alphasmartphone.dto.InquiryDTO;
import com.alphasmartphone.exception.ResourceNotFoundException;
import com.alphasmartphone.model.Inquiry;
import com.alphasmartphone.model.Phone;
import com.alphasmartphone.repository.InquiryRepository;
import com.alphasmartphone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryService {
    
    private final InquiryRepository inquiryRepository;
    private final PhoneRepository phoneRepository;
    private final PhoneService phoneService;
    
    public List<InquiryDTO> getAllInquiries() {
        return inquiryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public InquiryDTO getInquiryById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));
        return convertToDTO(inquiry);
    }
    
    public List<InquiryDTO> getInquiriesByStatus(Inquiry.InquiryStatus status) {
        return inquiryRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public InquiryDTO createInquiry(InquiryDTO inquiryDTO) {
        Inquiry inquiry = new Inquiry();
        inquiry.setName(inquiryDTO.getName());
        inquiry.setEmail(inquiryDTO.getEmail());
        inquiry.setPhoneNumber(inquiryDTO.getPhoneNumber());
        inquiry.setMessage(inquiryDTO.getMessage());
        inquiry.setStatus(Inquiry.InquiryStatus.NEW);
        
        if (inquiryDTO.getPhoneId() != null) {
            Phone phone = phoneRepository.findById(Objects.requireNonNull(inquiryDTO.getPhoneId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Phone not found with id: " + inquiryDTO.getPhoneId()));
            inquiry.setPhone(phone);
            phoneService.incrementInquiryCount(phone.getId());
        }
        
        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        return convertToDTO(savedInquiry);
    }
    
    public InquiryDTO updateInquiryStatus(Long id, Inquiry.InquiryStatus status, String adminNotes) {
        Inquiry inquiry = inquiryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));
        
        inquiry.setStatus(status);
        if (adminNotes != null) {
            inquiry.setAdminNotes(adminNotes);
        }
        
        Inquiry updatedInquiry = inquiryRepository.save(inquiry);
        return convertToDTO(updatedInquiry);
    }
    
    public void deleteInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));
        inquiryRepository.delete(Objects.requireNonNull(inquiry));
    }
    
    private InquiryDTO convertToDTO(Inquiry inquiry) {
        InquiryDTO dto = new InquiryDTO();
        dto.setId(inquiry.getId());
        dto.setName(inquiry.getName());
        dto.setEmail(inquiry.getEmail());
        dto.setPhoneNumber(inquiry.getPhoneNumber());
        dto.setMessage(inquiry.getMessage());
        dto.setStatus(inquiry.getStatus());
        dto.setAdminNotes(inquiry.getAdminNotes());
        dto.setCreatedAt(inquiry.getCreatedAt());
        
        if (inquiry.getPhone() != null) {
            dto.setPhoneId(inquiry.getPhone().getId());
            dto.setPhoneName(inquiry.getPhone().getName());
        }
        
        return dto;
    }
}
