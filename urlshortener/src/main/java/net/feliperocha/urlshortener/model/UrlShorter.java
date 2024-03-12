package net.feliperocha.urlshortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class UrlShorter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longURL;
    private String shortURLId;

    public UrlShorter(String longURL) {
        this.longURL = longURL;
        this.shortURLId = UUID.randomUUID().toString();
    }
}
