package ru.ilkras.budcat.utilities;

public class URLFormatter {

    /**
     * Checks if the link has a scheme, and adds http:// if it doesn't
     * @param url url to format
     * @return formatted url
     */
    public String addHttpIfNeeded(String url) {
        if (!url.toLowerCase().matches("^\\w+://.*")) {
            url = "http://" + url;
        }
        return url;
    }
}
