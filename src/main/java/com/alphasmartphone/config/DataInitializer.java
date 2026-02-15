package com.alphasmartphone.config;

import com.alphasmartphone.model.Admin;
import com.alphasmartphone.model.Phone;
import com.alphasmartphone.repository.AdminRepository;
import com.alphasmartphone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final AdminRepository adminRepository;
    private final PhoneRepository phoneRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        initializeAdmin();
        initializeSamplePhones();
    }
    
    private void initializeAdmin() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Alpha@2025"));
            admin.setEmail("alpha.smartphone.cz@gmail.com");
            admin.setFullName("Alpha SmartPhone Admin");
            admin.setRole(Admin.Role.ADMIN);
            admin.setIsActive(true);
            
            adminRepository.save(admin);
            log.info("Default admin user created successfully");
        }
    }
    
    private void initializeSamplePhones() {
        if (phoneRepository.count() == 0) {
            Phone[] phones = {
                createPhone("iPhone 15 Pro Max", "Apple", "15 Pro Max", "BRAND_NEW", 
                    "Latest iPhone with A17 Pro chip and titanium design", 
                    "6.7 inches", "A17 Pro", "8GB", "256GB", "4422 mAh",
                    "48MP + 12MP + 12MP", "12MP", "iOS 17", "5G", true),
                    
                createPhone("Samsung Galaxy S24 Ultra", "Samsung", "S24 Ultra", "BRAND_NEW",
                    "Flagship Samsung with S Pen and 200MP camera",
                    "6.8 inches", "Snapdragon 8 Gen 3", "12GB", "512GB", "5000 mAh",
                    "200MP + 50MP + 12MP + 10MP", "12MP", "Android 14", "5G", true),
                    
                createPhone("Google Pixel 8 Pro", "Google", "8 Pro", "BRAND_NEW",
                    "Google's latest with advanced AI features and pure Android",
                    "6.7 inches", "Google Tensor G3", "12GB", "256GB", "5050 mAh",
                    "50MP + 48MP + 48MP", "10.5MP", "Android 14", "5G", true),
                    
                createPhone("OnePlus 12", "OnePlus", "12", "BRAND_NEW",
                    "Flagship killer with Hasselblad camera and fast charging",
                    "6.82 inches", "Snapdragon 8 Gen 3", "16GB", "512GB", "5400 mAh",
                    "50MP + 64MP + 48MP", "32MP", "Android 14", "5G", false),
                    
                createPhone("Xiaomi 14 Ultra", "Xiaomi", "14 Ultra", "BRAND_NEW",
                    "Premium Xiaomi with Leica optics and stunning display",
                    "6.73 inches", "Snapdragon 8 Gen 3", "16GB", "512GB", "5300 mAh",
                    "50MP + 50MP + 50MP + 50MP", "32MP", "Android 14", "5G", true),
                    
                createPhone("Samsung Galaxy Z Fold 5", "Samsung", "Z Fold 5", "BRAND_NEW",
                    "Revolutionary foldable phone with dual displays",
                    "7.6 inches", "Snapdragon 8 Gen 2", "12GB", "512GB", "4400 mAh",
                    "50MP + 12MP + 10MP", "10MP + 4MP", "Android 13", "5G", true),
                    
                createPhone("iPhone 14 Pro", "Apple", "14 Pro", "LIKE_NEW",
                    "Previous gen iPhone with excellent camera system",
                    "6.1 inches", "A16 Bionic", "6GB", "256GB", "3200 mAh",
                    "48MP + 12MP + 12MP", "12MP", "iOS 17", "5G", false),
                    
                createPhone("Oppo Find X6 Pro", "Oppo", "Find X6 Pro", "BRAND_NEW",
                    "Premium Oppo with Hasselblad collaboration",
                    "6.82 inches", "Snapdragon 8 Gen 2", "16GB", "512GB", "5000 mAh",
                    "50MP + 50MP + 50MP", "32MP", "Android 13", "5G", false)
            };
            
            phoneRepository.saveAll(Objects.requireNonNull(Arrays.asList(phones)));
            log.info("Sample phones created successfully");
        }
    }
    
    private Phone createPhone(String name, String brand, String model, String condition,
                             String description, String display, String processor,
                             String ram, String storage, String battery,
                             String mainCamera, String frontCamera, String os,
                             String network, boolean featured) {
        Phone phone = new Phone();
        phone.setName(name);
        phone.setBrand(brand);
        phone.setModel(model);
        phone.setCondition(Phone.PhoneCondition.valueOf(condition));
        phone.setDescription(description);
        phone.setDisplaySize(display);
        phone.setProcessor(processor);
        phone.setRam(ram);
        phone.setStorage(storage);
        phone.setBattery(battery);
        phone.setMainCamera(mainCamera);
        phone.setFrontCamera(frontCamera);
        phone.setOperatingSystem(os);
        phone.setNetwork(network);
        phone.setIsFeatured(featured);
        phone.setIsAvailable(true);
        phone.setImages(Arrays.asList(
            "/premium-black-smartphone.png",
            "/premium-silver-smartphone.png"
        ));
        return phone;
    }
}
