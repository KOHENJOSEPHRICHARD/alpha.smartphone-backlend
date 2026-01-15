package com.alphasmartphone.service;

import com.alphasmartphone.model.Review;
import com.alphasmartphone.model.Phone;
import com.alphasmartphone.repository.ReviewRepository;
import com.alphasmartphone.repository.PhoneRepository;
import com.alphasmartphone.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final PhoneRepository phoneRepository;
    
    public Review createReview(Long phoneId, Review review) {
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found"));
        review.setPhone(phone);
        return reviewRepository.save(review);
    }
    
    public List<Review> getPhoneReviews(Long phoneId) {
        return reviewRepository.findByPhoneId(phoneId);
    }
    
    public Double getAverageRating(Long phoneId) {
        Double avg = reviewRepository.getAverageRating(phoneId);
        return avg != null ? avg : 0.0;
    }
}
