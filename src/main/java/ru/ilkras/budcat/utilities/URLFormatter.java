package ru.ilkras.budcat.utilities;

public class URLFormatter {

    // FIXME: В чём назначение этого метода?
    public String format(String url) {
        if (!url.toLowerCase().matches("^\\w+://.*")) {
            url = "http://" + url;
        }
        return url;
    }
}
