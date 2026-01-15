package com.alphasmartphone.service;

import com.alphasmartphone.model.AuditLog;
import com.alphasmartphone.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    
    private final AuditLogRepository auditLogRepository;
    
    @Transactional
    public AuditLog createLog(String action, String entityType, Long entityId, 
                             String performedBy, String details, String ipAddress) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setPerformedBy(performedBy);
        log.setDetails(details);
        log.setIpAddress(ipAddress);
        return auditLogRepository.save(log);
    }
    
    public List<AuditLog> getRecentLogs(int hours) {
        LocalDateTime startDate = LocalDateTime.now().minusHours(hours);
        return auditLogRepository.findRecentLogs(startDate);
    }
    
    public List<AuditLog> getLogsByEntity(String entityType, Long entityId) {
        return auditLogRepository.findByEntityTypeAndEntityIdOrderByTimestampDesc(entityType, entityId);
    }
}
