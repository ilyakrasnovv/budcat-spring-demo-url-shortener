package ru.ilkras.budcat.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilkras.budcat.BudcatApplication;
import ru.ilkras.budcat.data.NotOnlyEnv;

@RestController
public class Routing {
    @GetMapping("/")
    public String base() {
        if (BudcatApplication.NOE.debugEnpoitsEnabled) {
            return BudcatApplication.NOE.baseUrl;
        }
        return "";
    }
}
