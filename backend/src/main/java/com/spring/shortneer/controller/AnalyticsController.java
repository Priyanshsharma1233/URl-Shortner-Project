package com.spring.shortneer.controller;

import com.spring.shortneer.dto.AnalyticsResponseDto;
import com.spring.shortneer.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/api/analytics/{shortCode}")
    public AnalyticsResponseDto getAnalytics(@PathVariable String shortCode) {
        return analyticsService.getAnalytics(shortCode);
    }
}