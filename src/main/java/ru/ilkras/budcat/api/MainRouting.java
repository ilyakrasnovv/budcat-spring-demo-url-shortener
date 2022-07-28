package ru.ilkras.budcat.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ilkras.budcat.BudcatApplication;
import ru.ilkras.budcat.data.UrlsBondsManager;
import ru.ilkras.budcat.models.UrlsBond;
import ru.ilkras.budcat.utilities.URLFormatter;

import java.net.URI;

@RestController
public class MainRouting {
    final UrlsBondsManager bonds = new UrlsBondsManager();

    @GetMapping("url/shorten")
    public UrlsBond shortenUrl(@RequestParam("longUrl") String longUrl) {
        return bonds.shortenUrl(URLFormatter.addHttpIfNeeded(longUrl));
    }

    @GetMapping("url/expand")
    public UrlsBond expandUrl(@RequestParam("id") Long id) {
        return bonds.expandUrl(id);
    }

    @GetMapping("u/{id}")
    public ResponseEntity<?> redirecting(@PathVariable("id") String s) {
        try {
            Long id = Long.parseLong(s);
            String longUrl = bonds.expandUrl(id).getOrigin();
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(longUrl)).build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @RequestMapping("/")
    public ResponseEntity<?> forms() {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/index.html")).build();
    }
}
