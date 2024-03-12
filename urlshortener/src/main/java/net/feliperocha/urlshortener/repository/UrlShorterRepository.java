package net.feliperocha.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.feliperocha.urlshortener.model.URLShorter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShorterRepository extends JpaRepository<URLShorter, Long> {
    Optional<URLShorter> findByLongURL(String longURL);

    Optional<URLShorter> findByShortURLId(String shortURLId);
    
}