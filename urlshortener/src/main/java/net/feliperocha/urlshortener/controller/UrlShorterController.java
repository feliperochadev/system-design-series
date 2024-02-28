package net.feliperocha.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.feliperocha.urlshortener.service.UrlShorterService;

@RestController
@RequiredArgsConstructor
public class UrlShorterController {

    private final UrlShorterService service;

    @PostMapping("/api/v1/short")
    public String shortenUrl(@RequestParam("longUrl") String longUrl) {
        return service.shortenUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity redirect(@PathVariable("shortUrl") String shortUrl) {
        return service.getLongUrl(shortUrl)
                .map(longUrl -> ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .header(HttpHeaders.LOCATION, longUrl)
                        .build())
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }
}

