package com.spring.shortneer.service;

import com.spring.shortneer.dto.AnalyticsResponseDto;
import com.spring.shortneer.dto.DailyClickDto;
import com.spring.shortneer.entity.DailyClickCount;
import com.spring.shortneer.exception.ShortCodeNotFoundException;
import com.spring.shortneer.repository.DailyClickCountRepository;
import com.spring.shortneer.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private static final int DAYS_TO_SHOW = 30;

    private final UrlRepository urlRepository;
    private final DailyClickCountRepository dailyClickCountRepository;

    public AnalyticsResponseDto getAnalytics(String shortCode) {

        urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortCodeNotFoundException(
                        "No URL found for short code: " + shortCode));

        List<DailyClickCount> rows = dailyClickCountRepository.findByShortCode(shortCode);

        long total = 0;
        Map<LocalDate, Integer> countsByDate = new HashMap<>();
        for (DailyClickCount row : rows) {
            total += row.getClickCount();
            countsByDate.put(row.getDate(), row.getClickCount());
        }

        List<DailyClickDto> clicksByDay = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = DAYS_TO_SHOW - 1; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            clicksByDay.add(new DailyClickDto(day, countsByDate.getOrDefault(day, 0)));
        }

        return new AnalyticsResponseDto(shortCode, total, clicksByDay);
    }
}