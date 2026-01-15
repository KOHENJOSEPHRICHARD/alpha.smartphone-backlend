package com.alphasmartphone.service;

import com.alphasmartphone.dto.AnalyticsDTO;
import com.alphasmartphone.model.Analytics;
import com.alphasmartphone.model.Phone;
import com.alphasmartphone.repository.AnalyticsRepository;
import com.alphasmartphone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    
    private final AnalyticsRepository analyticsRepository;
    private final PhoneRepository phoneRepository;
    
    @Transactional
    public void trackEvent(Long phoneId, String eventType, String userAgent, String ipAddress) {
        Phone phone = phoneRepository.findById(phoneId).orElse(null);
        if (phone == null) return;
        
        Analytics analytics = new Analytics();
        analytics.setPhone(phone);
        analytics.setEventType(eventType);
        analytics.setUserAgent(userAgent);
        analytics.setIpAddress(ipAddress);
        analyticsRepository.save(analytics);
    }
    
    public AnalyticsDTO getDashboardAnalytics() {
        LocalDateTime last30Days = LocalDateTime.now().minusDays(30);
        
        Long totalViews = analyticsRepository.countEventsSince("VIEW", last30Days);
        Long totalInquiries = analyticsRepository.countEventsSince("INQUIRY", last30Days);
        Long totalWhatsAppClicks = analyticsRepository.countEventsSince("WHATSAPP_CONTACT", last30Days);
        Long totalProducts = phoneRepository.count();
        
        AnalyticsDTO dto = new AnalyticsDTO();
        dto.setTotalProducts(totalProducts);
        dto.setTotalViews(totalViews);
        dto.setTotalInquiries(totalInquiries);
        dto.setTotalWhatsAppClicks(totalWhatsAppClicks);
        
        return dto;
    }
    
    public Map<String, Object> getTopPerformingProducts() {
        List<Object[]> topViewed = analyticsRepository.findTopViewedPhones();
        List<Object[]> topInquired = analyticsRepository.findTopInquiredPhones();
        
        Map<String, Object> result = new HashMap<>();
        result.put("topViewed", topViewed);
        result.put("topInquired", topInquired);
        
        return result;
    }
}
