package com.spring.shortneer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "daily_click_count",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"short_code", "date_for_url"}
        )
)
public class DailyClickCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", nullable = false, length = 8)
    private String shortCode;

    @Column(name = "date_for_url", nullable = false)
    private LocalDate date;

    @Column(name = "click_count", nullable = false)
    private int clickCount = 0;

}