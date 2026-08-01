package net.feliperocha.urlshortener.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import net.feliperocha.urlshortener.repository.URLShorterRepository;
import net.feliperocha.urlshortener.model.URLShorter;

@Service
    @RequiredArgsConstructor
    public class URLShorterService {

private final URLShorterRepository repository;
        private final ShortURLIdGenerator idGenerator;
        private final RedisTemplate<String, String> redisTemplate;

@Value("${urlshortener.baseshorturlpath}")
        private String BASE_SHORT_URL_PATH;

@Value("${urlshortener.cache.ttl-hours:24}")
        private long cacheTtlHours;

private static final String CACHE_PREFIX = "shorturl:";

public String shortenURL(String longURL) {
    var optionalUrlShorter = repository.findByLongURL(longURL);
    if (optionalUrlShorter.isPresent()) {
        return buildShortURL(optionalUrlShorter.get().getShortURLId());
    }

        String shortURLId = idGenerator.generate();
    repository.save(new URLShorter(longURL, shortURLId));

        // Proactively cache on write, avoids a cache miss on first access
        cacheURL(shortURLId, longURL);

        return buildShortURL(shortURLId);
}

public Optional<String> getLongURL(String shortURLId) {
    // 1. Check cache first
        String cachedLongURL = redisTemplate.opsForValue().get(CACHE_PREFIX + shortURLId);
    if (cachedLongURL != null) {
        return Optional.of(cachedLongURL);
    }

        // 2. Cache miss: fetch from DB and populate cache
        return repository.findByShortURLId(shortURLId)
            .map(urlShorter -> {
                cacheURL(shortURLId, urlShorter.getLongURL());
                return urlShorter.getLongURL();
            });
}

private void cacheURL(String shortURLId, String longURL) {
    redisTemplate.opsForValue().set(
        CACHE_PREFIX + shortURLId,
        longURL,
        cacheTtlHours,
        TimeUnit.HOURS
        );
}

private String buildShortURL(String shortURLId) {
    return BASE_SHORT_URL_PATH + "/" + shortURLId;
}
    }
