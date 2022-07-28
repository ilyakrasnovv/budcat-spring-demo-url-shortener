package ru.ilkras.budcat.models;

import ru.ilkras.budcat.BudcatApplication;

public class UrlsBond {
    private String origin;
    private String shortened;

    public UrlsBond(String origin, String shortened) {
        this.origin = origin;
        this.shortened = shortened;
    }

    public String getOrigin() {
        return origin;
    }

    public String getShortened() {
        return shortened;
    }
}
