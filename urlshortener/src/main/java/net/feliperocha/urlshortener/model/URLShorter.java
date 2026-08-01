package net.feliperocha.urlshortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
    @Getter
    @RequiredArgsConstructor
    public class URLShorter {

@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

private String longURL;
        private String shortURLId;

public URLShorter(String longURL, String shortURLId) {
    this.longURL = longURL;
    this.shortURLId = shortURLId;
}
    }
