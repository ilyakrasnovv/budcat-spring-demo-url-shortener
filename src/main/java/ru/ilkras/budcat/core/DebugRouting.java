package ru.ilkras.budcat.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilkras.budcat.BudcatApplication;

import java.util.HashMap;

@RestController
public class DebugRouting {
    @GetMapping("/base_url")
    public String base() {
        if (BudcatApplication.NOE.debugEndpointsEnabled) {
            return BudcatApplication.NOE.baseUrl;
        }
        return "";
    }
}
