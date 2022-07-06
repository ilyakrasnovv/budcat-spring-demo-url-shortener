package ru.ilkras.budcat.data;

import ru.ilkras.budcat.models.DbUrlsBond;
import ru.ilkras.budcat.models.UrlsBond;
import ru.ilkras.budcat.utilities.DoubleMap;

import java.util.HashMap;

import static java.lang.Long.max;

public class UrlsBondsManager {
    DbManager db;
    DoubleMap<String, Long> map;
    Long maxId = -1L;


    public UrlsBondsManager() {
        db = new DbManager();
        HashMap<String, Long> recoveredFromDB = new HashMap<>();
        HashMap<Long, String> recoveredFromDBReversed = new HashMap<>();
        for (DbUrlsBond it : db.recoverBonds()) {
            maxId = max(maxId, it.getId());
            recoveredFromDB.put(it.getOrigin(), it.getId());
            recoveredFromDBReversed.put(it.getId(), it.getOrigin());
        }
        map = new DoubleMap(recoveredFromDB, recoveredFromDBReversed,
            (DoubleMap.OnAdd<String, Long>) ((String s, Long i) -> db.addUrlsBond(new DbUrlsBond(s, i))
        ));
    }

    public UrlsBond shortenUrl(String url) {
        Long id = map.get(url);
        if (id == null) {
            id = ++maxId;
            map.add(url, id);
        }
        return new UrlsBond(url, id);
    }

    public UrlsBond expandUrl(Long id) {
        String url = map.rget(id);
        if (url == null)
            throw new RuntimeException("Cannot find shortened url with id " + id);
        return new UrlsBond(map.rget(id), id);
    }
}
