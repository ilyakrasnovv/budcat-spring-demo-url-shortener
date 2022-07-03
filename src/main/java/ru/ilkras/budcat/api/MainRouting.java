package ru.ilkras.budcat.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ilkras.budcat.data.UrlsBondsManager;
import ru.ilkras.budcat.models.UrlsBond;

import java.net.URI;

@RestController
public class MainRouting {
    final UrlsBondsManager bonds = new UrlsBondsManager();

    @GetMapping("url/shorten")
    public UrlsBond shortenUrl(@RequestParam("longUrl") String id) {
        return bonds.shortenUrl(id);
    }

    @GetMapping("url/expand")
    public UrlsBond expandUrl(@RequestParam("id") Long id) {
        return bonds.expandUrl(id);
    }

    @GetMapping("u/{id}")
    public ResponseEntity<?> redirecting(@PathVariable("id") Long id) {
        String longUrl = bonds.expandUrl(id).getOrigin();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
    }
}
