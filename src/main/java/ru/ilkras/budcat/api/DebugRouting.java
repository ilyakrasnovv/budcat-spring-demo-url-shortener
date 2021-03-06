package ru.ilkras.budcat.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.ilkras.budcat.BudcatApplication;

@RestController
public class DebugRouting {
    @GetMapping("/debug/base_url")
    public String base() {
        if (BudcatApplication.NOE.getDebugEndpointsEnabled()) {
            return BudcatApplication.NOE.getBaseUrl();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
