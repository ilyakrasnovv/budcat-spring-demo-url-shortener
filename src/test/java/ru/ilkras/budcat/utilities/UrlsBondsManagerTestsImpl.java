package ru.ilkras.budcat.utilities;

import ru.ilkras.budcat.data.DuplicatesFoundException;
import ru.ilkras.budcat.data.UrlsBondsManagerInterface;
import ru.ilkras.budcat.models.DbUrlsBond;
import ru.ilkras.budcat.models.UrlsBond;

public class UrlsBondsManagerTestsImpl implements UrlsBondsManagerInterface {
    MapBondsSaver saver = new MapBondsSaver();
    private Long id = -1L;
    public UrlsBondsManagerTestsImpl() {}

    @Override
    public UrlsBond shortenUrl(String url) {
        url = URLFormatter.addHttpIfNeeded(url);
        if (saver.loadBondByOrigin(url) == null) {
            saver.addUrlsBond(new DbUrlsBond(url, ++id));
        }
        return new UrlsBond(url, URLFormatter.createShortenedById(saver.loadBondByOrigin(url).getId()));
    }

    @Override
    public UrlsBond expandUrl(Long id) {
        return new UrlsBond(saver.loadBondById(id).getOrigin(), URLFormatter.createShortenedById(id));
    }
}
