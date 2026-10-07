package com.spring.shortneer.dto;

import java.util.List;

public record AnalyticsResponseDto(
        String shortCode,
        long totalClicks,
        List<DailyClickDto> clicksByDay
) {
}
