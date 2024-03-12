package net.feliperocha.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.feliperocha.urlshortener.model.UrlShorter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShorterRepository extends JpaRepository<UrlShorter, Long> {
    Optional<UrlShorter> findByLongURL(String longURL);

    Optional<UrlShorter> findByShortURLId(String shortURLId);
    
}