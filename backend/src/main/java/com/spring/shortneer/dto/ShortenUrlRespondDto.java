package com.spring.shortneer.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ShortenUrlRespondDto {
    private String shortCode;
}
