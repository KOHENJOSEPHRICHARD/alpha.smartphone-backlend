package com.alphasmartphone.repository;

import com.alphasmartphone.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPhoneId(Long phoneId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.phone.id = ?1")
    Double getAverageRating(Long phoneId);
}
