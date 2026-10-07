package com.spring.shortneer.repository;

import com.spring.shortneer.entity.DailyClickCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyClickCountRepository extends JpaRepository<DailyClickCount, Long> {
    Optional<DailyClickCount> findByShortCodeAndDate(String shortCode, LocalDate date);
    List<DailyClickCount> findByShortCode(String shortCode);
}