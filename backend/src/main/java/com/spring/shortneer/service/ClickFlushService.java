package com.spring.shortneer.service;

import com.spring.shortneer.entity.DailyClickCount;
import com.spring.shortneer.repository.DailyClickCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClickFlushService {

    private final StringRedisTemplate redisTemplate;              // 1
    private final DailyClickCountRepository dailyClickCountRepository;        // 2

    @Scheduled(fixedRate = 5000)                           // 3. milliseconds
    public void flushClicksToDatabase() {

        Set<String> keys = redisTemplate.keys("clicks:*");          // 4

        if (keys == null) {
            return;
        }

        for (String key : keys) {
            try {
                flushOneKey(key);
            } catch (Exception e) {
                log.warn("Failed to flush key {}", key, e);
            }
        }
    }

    private void flushOneKey(String key) {

        String[] parts = key.split(":");                  // 5. what to split on

        String shortCode = parts[1];                    // 6. which index
        LocalDate date = LocalDate.parse(parts[2]);      // 7. which index

        String rawCount = redisTemplate.opsForValue().get(key);      // 8
        if (rawCount == null) {
            return;
        }
        int count = Integer.parseInt(rawCount);

        DailyClickCount row = dailyClickCountRepository
                .findByShortCodeAndDate(shortCode, date)
                .orElseGet(() -> {
                    DailyClickCount newRow = new DailyClickCount();
                    newRow.setShortCode(shortCode);              // 10
                    newRow.setDate(date);                   // 11
                    return newRow;
                });

        row.setClickCount(row.getClickCount() + count );      // 12

        dailyClickCountRepository.save(row);                                     // 13
        redisTemplate.delete(key);                                   // 14
    }
}