package com.alphasmartphone.service;

import com.alphasmartphone.model.Wishlist;
import com.alphasmartphone.model.Phone;
import com.alphasmartphone.repository.WishlistRepository;
import com.alphasmartphone.repository.PhoneRepository;
import com.alphasmartphone.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistService {
    
    private final WishlistRepository wishlistRepository;
    private final PhoneRepository phoneRepository;
    
    public Wishlist addToWishlist(Long phoneId, String sessionId) {
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found"));
        
        Wishlist wishlist = new Wishlist();
        wishlist.setPhone(phone);
        wishlist.setSessionId(sessionId);
        return wishlistRepository.save(wishlist);
    }
    
    public List<Wishlist> getUserWishlist(String sessionId) {
        return wishlistRepository.findBySessionId(sessionId);
    }
    
    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}
