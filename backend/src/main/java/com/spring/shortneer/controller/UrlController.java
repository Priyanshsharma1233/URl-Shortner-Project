package com.spring.shortneer.controller;

import com.spring.shortneer.dto.ShortenUrlRequestDto;
import com.spring.shortneer.dto.ShortenUrlRespondDto;
import com.spring.shortneer.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ShortenUrlRespondDto shortenUrl(
            @Valid @RequestBody ShortenUrlRequestDto requestDto) {

        return urlService.shortenUrl(requestDto);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getRedirectionUrl(@PathVariable String shortCode) {
        // 302, not 301: browsers cache 301s, which would skip our server
        // on repeat visits and break click tracking.
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(urlService.getRedirectionUri(shortCode))
                .build();
    }
}