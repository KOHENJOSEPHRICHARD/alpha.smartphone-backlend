package com.alphasmartphone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDTO {
    private Long totalProducts;
    private Long totalViews;
    private Long totalInquiries;
    private Long totalWhatsAppClicks;
    private String estimatedRevenue;
}
