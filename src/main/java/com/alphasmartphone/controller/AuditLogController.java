package com.alphasmartphone.controller;

import com.alphasmartphone.dto.ApiResponse;
import com.alphasmartphone.model.AuditLog;
import com.alphasmartphone.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuditLogController {
    
    private final AuditLogService auditLogService;
    
    @GetMapping("/recent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getRecentLogs(@RequestParam(defaultValue = "24") int hours) {
        List<AuditLog> logs = auditLogService.getRecentLogs(hours);
        return ResponseEntity.ok(ApiResponse.success("Recent logs retrieved", logs));
    }
    
    @GetMapping("/entity/{entityType}/{entityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getEntityLogs(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        List<AuditLog> logs = auditLogService.getLogsByEntity(entityType, entityId);
        return ResponseEntity.ok(ApiResponse.success("Entity logs retrieved", logs));
    }
}
