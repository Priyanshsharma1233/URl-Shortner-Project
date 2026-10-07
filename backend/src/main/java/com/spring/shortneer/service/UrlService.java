package com.spring.shortneer.service;

import com.spring.shortneer.dto.ShortenUrlRequestDto;
import com.spring.shortneer.dto.ShortenUrlRespondDto;
import com.spring.shortneer.entity.UrlEntity;
import com.spring.shortneer.exception.InvalidUrlException;
import com.spring.shortneer.exception.ShortCodeGenerationException;
import com.spring.shortneer.exception.ShortCodeNotFoundException;
import com.spring.shortneer.repository.UrlRepository;
import com.spring.shortneer.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private static final int MAX_ATTEMPTS = 5;
    private static final int SHORT_CODE_LENGTH = 8;

    private final UrlUtils urlUtils;
    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;

    public ShortenUrlRespondDto shortenUrl(ShortenUrlRequestDto requestDto) {

        String url = requestDto.getUrl();

        if (!urlUtils.isValid(url)) {
            throw new InvalidUrlException("URL is invalid: " + url);
        }

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                String shortCode = RandomStringUtils.randomAlphabetic(SHORT_CODE_LENGTH);

                UrlEntity urlEntity = new UrlEntity();
                urlEntity.setMainUrl(url);
                urlEntity.setShortCode(shortCode);

                urlRepository.save(urlEntity);

                return ShortenUrlRespondDto.builder()
                        .shortCode(shortCode)
                        .build();

            } catch (DataIntegrityViolationException e) {
                // Short code collision: loop and try a new code.
            }
        }

        throw new ShortCodeGenerationException(
                "Unable to generate a unique short code after " + MAX_ATTEMPTS + " attempts");
    }

    // Deliberately NOT @Transactional: a failed Redis increment must not
    // roll back or break the redirect.
    public URI getRedirectionUri(String shortCode) {

        String mainUrl = urlRepository.findByShortCode(shortCode)
                .map(UrlEntity::getMainUrl)
                .orElseThrow(() -> new ShortCodeNotFoundException(
                        "No URL found for short code: " + shortCode));

        recordClick(shortCode);

        return URI.create(mainUrl);
    }

    private void recordClick(String shortCode) {
        try {
            String key = "clicks:" + shortCode + ":" + LocalDate.now();
            redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            log.warn("Failed to record click for short code {}", shortCode, e);
        }
    }
}