package net.feliperocha.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import net.feliperocha.urlshortener.repository.UrlShorterRepository;
import net.feliperocha.urlshortener.model.UrlShorter;


@Service
@RequiredArgsConstructor
public class UrlShorterService {

    private final UrlShorterRepository repository;

    @Value("urlshortener.baseshorturlpath")
    private final String BASE_SHORT_URL_PATH;

    public String shortenUrl(String longUrl) {
        var optionalUrlShorter = repository.findByLongUrl(longUrl);
        if (optionalUrlShorter.isPresent()) {
            return buildShortURL(optionalUrlShorter.get().getShortURL());
        }

        var urlShorter = repository.save(new UrlShorter(longUrl));
        return buildShortURL(urlShorter.getShortURL());
    }

    public Optional<String> getLongUrl(String shortUrlId) {
        return repository.findByShortUrlId(shortUrlId)
                .flatMap(urlShorter -> Optional.of(urlShorter.getLongURL()));
    }

    private String buildShortURL(String shortUrlId) {
        return BASE_SHORT_URL_PATH + "/" + shortUrlId;
    }
}

