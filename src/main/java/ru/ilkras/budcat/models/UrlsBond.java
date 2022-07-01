package ru.ilkras.budcat.models;


public class UrlsBond {
    private String origin;
    private String shortened;

    public UrlsBond(String _origin, String _shortened) {
        origin = _origin;
        shortened = _shortened;
    }

    public String getOrigin() {
        return origin;
    }

    public String getShortened() {
        return shortened;
    }
}