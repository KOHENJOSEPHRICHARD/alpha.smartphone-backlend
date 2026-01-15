package com.alphasmartphone.service;

import com.alphasmartphone.dto.ComparisonDTO;
import com.alphasmartphone.dto.PhoneDTO;
import com.alphasmartphone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComparisonService {
    
    private final PhoneRepository phoneRepository;
    private final PhoneService phoneService;
    
    public ComparisonDTO comparePhones(List<Long> phoneIds) {
        List<PhoneDTO> phones = phoneIds.stream()
                .map(phoneService::getPhoneById)
                .collect(Collectors.toList());
        
        List<String> specs = new ArrayList<>();
        specs.addAll(List.of(
                "Display Size", "Processor", "RAM", "Storage", 
                "Battery", "Main Camera", "Front Camera", 
                "Operating System", "Network"
        ));
        
        ComparisonDTO dto = new ComparisonDTO();
        dto.setPhones(phones);
        dto.setSpecs(specs);
        return dto;
    }
}
