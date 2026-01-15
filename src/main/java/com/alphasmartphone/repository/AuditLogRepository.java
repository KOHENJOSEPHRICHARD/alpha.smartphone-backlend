package com.alphasmartphone.repository;

import com.alphasmartphone.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByPerformedByOrderByTimestampDesc(String performedBy);
    
    List<AuditLog> findByEntityTypeAndEntityIdOrderByTimestampDesc(String entityType, Long entityId);
    
    @Query("SELECT a FROM AuditLog a WHERE a.timestamp >= :startDate ORDER BY a.timestamp DESC")
    List<AuditLog> findRecentLogs(LocalDateTime startDate);
    
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.action = :action AND a.timestamp >= :startDate")
    Long countActionsSince(String action, LocalDateTime startDate);
}
