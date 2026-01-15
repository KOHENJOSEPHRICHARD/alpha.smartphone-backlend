package com.alphasmartphone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    
    public void sendInquiryNotification(String customerName, String customerEmail, String phoneModel) {
        log.info("=== Email Notification ===");
        log.info("New inquiry received from: {} ({})", customerName, customerEmail);
        log.info("Product interest: {}", phoneModel);
        log.info("========================");
        
        // In production, integrate with actual email service:
        // - JavaMailSender (SMTP)
        // - SendGrid API
        // - AWS SES
        // - Mailgun, etc.
    }
    
    public void sendWelcomeEmail(String adminEmail, String adminName) {
        log.info("=== Welcome Email ===");
        log.info("Sending welcome email to: {} ({})", adminName, adminEmail);
        log.info("====================");
    }
}
