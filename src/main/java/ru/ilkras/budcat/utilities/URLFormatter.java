package ru.ilkras.budcat.utilities;

public class URLFormatter {
    public String format(String url) {
        if (!url.toLowerCase().matches("^\\w+://.*")) {
            url = "http://" + url;
        }
        return url;
    }
}
