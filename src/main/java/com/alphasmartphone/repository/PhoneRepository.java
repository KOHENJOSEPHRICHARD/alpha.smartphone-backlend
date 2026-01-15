package com.alphasmartphone.repository;

import com.alphasmartphone.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    
    List<Phone> findByBrand(String brand);
    
    List<Phone> findByCondition(Phone.PhoneCondition condition);
    
    List<Phone> findByIsFeaturedTrue();
    
    List<Phone> findByIsAvailableTrue();
    
    @Query("SELECT p FROM Phone p WHERE p.isAvailable = true ORDER BY p.createdAt DESC")
    List<Phone> findLatestAvailablePhones();
    
    @Query("SELECT p FROM Phone p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Phone> searchPhones(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Phone p WHERE " +
           "(:brand IS NULL OR p.brand = :brand) AND " +
           "(:condition IS NULL OR p.condition = :condition) AND " +
           "p.isAvailable = true")
    List<Phone> filterPhones(@Param("brand") String brand, 
                             @Param("condition") Phone.PhoneCondition condition);
}
