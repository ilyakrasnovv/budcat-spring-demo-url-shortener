package ru.ilkras.budcat.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return bonds.shortenUrl(new URLFormatter().format(longUrl));
    }

    @GetMapping("url/expand")
    public UrlsBond expandUrl(@RequestParam("id") Long id) {
        return bonds.expandUrl(id);
    }

    @GetMapping("u/{id}")
    public ResponseEntity<?> redirecting(@PathVariable("id") Long id) {
        String longUrl = bonds.expandUrl(id).getOrigin();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(longUrl)).build();
    }

    @RequestMapping("/")
    @ResponseBody
    public String forms() {
        return BudcatApplication.NOE.formHtml;
    }
}
