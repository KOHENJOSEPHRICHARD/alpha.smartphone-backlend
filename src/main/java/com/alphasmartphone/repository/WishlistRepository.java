package com.alphasmartphone.repository;

import com.alphasmartphone.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findBySessionId(String sessionId);
}
