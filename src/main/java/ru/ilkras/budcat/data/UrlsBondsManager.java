package ru.ilkras.budcat.data;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import ru.ilkras.budcat.models.DbUrlsBond;
import ru.ilkras.budcat.models.UrlsBond;
import ru.ilkras.budcat.utilities.DoubleMapCache;
import ru.ilkras.budcat.utilities.URLFormatter;

import java.util.concurrent.TimeUnit;

import static java.lang.Long.max;

public class UrlsBondsManager {
    private final UrlBondsSavingManager db;
    private final DoubleMapCache<String, Long> map;
    private Long maxId = -1L;

    public UrlsBondsManager(UrlBondsSavingManager dbProvider) {
        db = dbProvider;
        Caffeine<Object, Object> builder = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(1, TimeUnit.DAYS);
        LoadingCache<String, Long> recoveredFromDB = builder.build(keyOrigin -> {
            DbUrlsBond loaded = db.loadBondByOrigin(keyOrigin);
            if (loaded == null)
                return null;
            return loaded.getId();
        });
        LoadingCache<Long, String> recoveredFromDBReversed = builder.build(keyId -> {
            DbUrlsBond loaded = db.loadBondById(keyId);
            if (loaded == null)
                return null;
            return loaded.getOrigin();
        });
        for (DbUrlsBond it : db.recoverBonds()) {
            maxId = max(maxId, it.getId());
            recoveredFromDB.put(it.getOrigin(), it.getId());
            recoveredFromDBReversed.put(it.getId(), it.getOrigin());
        }

        map = new DoubleMapCache(recoveredFromDB, recoveredFromDBReversed,
            (DoubleMapCache.OnAdd<String, Long>) ((String s, Long i) -> db.addUrlsBond(new DbUrlsBond(s, i))
        ));
    }

    public UrlsBond shortenUrl(String url) {
        Long id = map.get(url);
        if (id == null) {
            id = ++maxId;
            map.add(url, id);
        }
        return new UrlsBond(url, URLFormatter.createShortenedById(id));
    }

    public UrlsBond expandUrl(Long id) {
        String url = map.rget(id);
        if (url == null)
            throw new RuntimeException("Cannot find shortened url with id " + id);
        return new UrlsBond(map.rget(id), URLFormatter.createShortenedById(id));
    }
}
