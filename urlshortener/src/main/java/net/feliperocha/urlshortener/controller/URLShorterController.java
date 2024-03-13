package net.feliperocha.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.feliperocha.urlshortener.service.URLShorterService;

@RestController
@RequiredArgsConstructor
public class URLShorterController {

    private final URLShorterService service;

    @PostMapping("/api/v1/short")
    public String shortenUrl(@RequestParam("longUrl") String longURL) {
        return service.shortenURL(longURL);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity redirect(@PathVariable("shortUrl") String shortURL) {
        return service.getLongURL(shortURL)
                .map(longUrl -> ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .header(HttpHeaders.LOCATION, longUrl)
                        .build())
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }
}

