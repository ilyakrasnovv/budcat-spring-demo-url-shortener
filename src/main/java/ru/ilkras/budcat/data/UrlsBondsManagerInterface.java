package ru.ilkras.budcat.data;

import ru.ilkras.budcat.models.UrlsBond;

public interface UrlsBondsManagerInterface {
    UrlsBond shortenUrl(String url) throws DuplicatesFoundException;

    UrlsBond expandUrl(Long id) throws DuplicatesFoundException;
}
