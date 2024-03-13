package net.feliperocha.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

import net.feliperocha.urlshortener.repository.URLShorterRepository;
import net.feliperocha.urlshortener.model.URLShorter;


@Service
@RequiredArgsConstructor
public class URLShorterService {

    private final URLShorterRepository repository;

    @Value("${urlshortener.baseshorturlpath}")
    private String BASE_SHORT_URL_PATH;

    public String shortenURL(String longURL) {
        var optionalUrlShorter = repository.findByLongURL(longURL);
        if (optionalUrlShorter.isPresent()) {
            return buildShortURL(optionalUrlShorter.get().getShortURLId());
        }

        var urlShorter = repository.save(new URLShorter(longURL));
        return buildShortURL(urlShorter.getShortURLId());
    }

    public Optional<String> getLongURL(String shortURLId) {
        return repository.findByShortURLId(shortURLId)
                .flatMap(urlShorter -> Optional.of(urlShorter.getLongURL()));
    }

    private String buildShortURL(String shortURLId) {
        return BASE_SHORT_URL_PATH + "/" + shortURLId;
    }
}

