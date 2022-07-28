package ru.ilkras.budcat.utilities;

import ru.ilkras.budcat.BudcatApplication;

public class URLFormatter {

    /**
     * Checks if the link has a scheme, and adds http:// if it doesn't
     *
     * @param url url to format
     * @return formatted url
     */
    static public String addHttpIfNeeded(String url) {
        if (!url.toLowerCase().matches("^\\w+://.*")) {
            url = "http://" + url;
        }
        return url;
    }

    static public String createShortenedById(Long id) {
        return BudcatApplication.NOE.getBaseUrl() + "/u/" + id;
    }
}
