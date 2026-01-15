package com.alphasmartphone.repository;

import com.alphasmartphone.model.Analytics;
import com.alphasmartphone.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    
    Long countByPhoneAndEventType(Phone phone, String eventType);
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.eventType = :eventType AND a.timestamp >= :startDate")
    Long countEventsSince(String eventType, LocalDateTime startDate);
    
    @Query("SELECT a.phone, COUNT(a) as count FROM Analytics a WHERE a.eventType = 'VIEW' GROUP BY a.phone ORDER BY count DESC")
    List<Object[]> findTopViewedPhones();
    
    @Query("SELECT a.phone, COUNT(a) as count FROM Analytics a WHERE a.eventType = 'INQUIRY' GROUP BY a.phone ORDER BY count DESC")
    List<Object[]> findTopInquiredPhones();
}
