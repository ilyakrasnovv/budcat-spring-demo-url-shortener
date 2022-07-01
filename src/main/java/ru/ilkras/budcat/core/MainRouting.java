package ru.ilkras.budcat.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilkras.budcat.BudcatApplication;

import java.util.HashMap;

class WhatTheActualFuck {
    private String FUCK = "text";

    public String getFUCK() {
        return FUCK;
    }
}

@RestController
public class MainRouting {
    private final HashMap<String, Integer> shortener = new HashMap<>();

    @GetMapping("/wtf")
    public WhatTheActualFuck wtf() {
        return new WhatTheActualFuck();
    }
}
