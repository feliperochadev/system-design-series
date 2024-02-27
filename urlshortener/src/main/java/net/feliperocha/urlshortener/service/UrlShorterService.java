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

    @Value("urlshortener.basepath")
    private final String BASE_PATH;

    public String shortenUrl(String longUrl) {
        var optionalUrlShorter = repository.findByLongUrl(longUrl);

        if (optionalUrlShorter.isEmpty()) {
            var urlShorter = new UrlShorter();
            urlShorter.setLongURL(longUrl);
            urlShorter.setShortURI(UUID.randomUUID().toString());
            repository.save(urlShorter);
            return buildURL(urlShorter.getShortURI());
        }
        
        return buildURL(optionalUrlShorter.get().getShortURI());
    }

    public Optional<String> getLongUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .flatMap(urlShorter -> Optional.of(urlShorter.getLongURL()));
    }

    private String buildURL(String URL) {
        return BASE_PATH + "/" + URL;
    }
}

