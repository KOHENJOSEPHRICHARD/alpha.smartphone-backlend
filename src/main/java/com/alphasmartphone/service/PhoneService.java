package com.alphasmartphone.service;

import com.alphasmartphone.dto.PhoneDTO;
import com.alphasmartphone.exception.ResourceNotFoundException;
import com.alphasmartphone.model.Phone;
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
public class PhoneService {
    
    private final PhoneRepository phoneRepository;
    
    public List<PhoneDTO> getAllPhones() {
        return phoneRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PhoneDTO> getAvailablePhones() {
        return phoneRepository.findByIsAvailableTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PhoneDTO> getFeaturedPhones() {
        return phoneRepository.findByIsFeaturedTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PhoneDTO getPhoneById(Long id) {
        Phone phone = phoneRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found with id: " + id));
        phone.setViewCount(phone.getViewCount() + 1);
        phoneRepository.save(phone);
        return convertToDTO(phone);
    }
    
    public List<PhoneDTO> searchPhones(String keyword) {
        return phoneRepository.searchPhones(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PhoneDTO> filterPhones(String brand, Phone.PhoneCondition condition) {
        return phoneRepository.filterPhones(brand, condition).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PhoneDTO createPhone(PhoneDTO phoneDTO) {
        Phone phone = convertToEntity(phoneDTO);
        Phone savedPhone = phoneRepository.save(Objects.requireNonNull(phone));
        return convertToDTO(savedPhone);
    }
    
    public PhoneDTO updatePhone(Long id, PhoneDTO phoneDTO) {
        Phone phone = phoneRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found with id: " + id));
        
        updatePhoneFromDTO(phone, phoneDTO);
        Phone updatedPhone = phoneRepository.save(Objects.requireNonNull(phone));
        return convertToDTO(updatedPhone);
    }
    
    public void deletePhone(Long id) {
        Phone phone = phoneRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found with id: " + id));
        phoneRepository.delete(Objects.requireNonNull(phone));
    }
    
    public void incrementInquiryCount(Long id) {
        Phone phone = phoneRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found with id: " + id));
        phone.setInquiryCount(phone.getInquiryCount() + 1);
        phoneRepository.save(phone);
    }
    
    private PhoneDTO convertToDTO(Phone phone) {
        PhoneDTO dto = new PhoneDTO();
        dto.setId(phone.getId());
        dto.setName(phone.getName());
        dto.setBrand(phone.getBrand());
        dto.setModel(phone.getModel());
        dto.setDescription(phone.getDescription());
        dto.setCondition(phone.getCondition());
        dto.setImages(phone.getImages());
        dto.setDisplaySize(phone.getDisplaySize());
        dto.setDisplayType(phone.getDisplayType());
        dto.setProcessor(phone.getProcessor());
        dto.setRam(phone.getRam());
        dto.setStorage(phone.getStorage());
        dto.setBattery(phone.getBattery());
        dto.setMainCamera(phone.getMainCamera());
        dto.setFrontCamera(phone.getFrontCamera());
        dto.setOperatingSystem(phone.getOperatingSystem());
        dto.setNetwork(phone.getNetwork());
        dto.setSimType(phone.getSimType());
        dto.setColors(phone.getColors());
        dto.setWeight(phone.getWeight());
        dto.setDimensions(phone.getDimensions());
        dto.setIsFeatured(phone.getIsFeatured());
        dto.setIsAvailable(phone.getIsAvailable());
        dto.setViewCount(phone.getViewCount());
        dto.setInquiryCount(phone.getInquiryCount());
        dto.setTags(phone.getTags());
        dto.setCreatedAt(phone.getCreatedAt());
        dto.setUpdatedAt(phone.getUpdatedAt());
        return dto;
    }
    
    private Phone convertToEntity(PhoneDTO dto) {
        Phone phone = new Phone();
        updatePhoneFromDTO(phone, dto);
        return phone;
    }
    
    private void updatePhoneFromDTO(Phone phone, PhoneDTO dto) {
        phone.setName(dto.getName());
        phone.setBrand(dto.getBrand());
        phone.setModel(dto.getModel());
        phone.setDescription(dto.getDescription());
        phone.setCondition(dto.getCondition());
        phone.setImages(dto.getImages());
        phone.setDisplaySize(dto.getDisplaySize());
        phone.setDisplayType(dto.getDisplayType());
        phone.setProcessor(dto.getProcessor());
        phone.setRam(dto.getRam());
        phone.setStorage(dto.getStorage());
        phone.setBattery(dto.getBattery());
        phone.setMainCamera(dto.getMainCamera());
        phone.setFrontCamera(dto.getFrontCamera());
        phone.setOperatingSystem(dto.getOperatingSystem());
        phone.setNetwork(dto.getNetwork());
        phone.setSimType(dto.getSimType());
        phone.setColors(dto.getColors());
        phone.setWeight(dto.getWeight());
        phone.setDimensions(dto.getDimensions());
        phone.setIsFeatured(dto.getIsFeatured() != null ? dto.getIsFeatured() : false);
        phone.setIsAvailable(dto.getIsAvailable() != null ? dto.getIsAvailable() : true);
        phone.setTags(dto.getTags());
    }
}
