package ru.ilkras.budcat.data;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import ru.ilkras.budcat.models.DbUrlsBond;
import ru.ilkras.budcat.models.UrlsBond;
import ru.ilkras.budcat.utilities.DoubleMap;
import ru.ilkras.budcat.utilities.URLFormatter;

import java.util.concurrent.TimeUnit;

import static java.lang.Long.max;

public class UrlsBondsManager {
    private final DbManager db; // InterfaceDbManager db
    private final DoubleMap<String, Long> map;
    private Long maxId = -1L;

    public UrlsBondsManager(/* InterfaceDbManager db */) {
        db = new DbManager();
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

        // FIXME: Фактически это выглядит как прикрученный кэш поверх базы. Я бы сделал это иначе. Обычно идёт попытка прочитать что-то тз кэша,
        //  если она неудачная то загружается из базы. В случае добавления нового урла сначала пишешь в базу, потом добавляешь в кэш. Это
        //  важно когда ты используешь внешний кэш, типа Redis, что бы вдруг не получилось так, что второй web сервер в кластере вытащил
        //  чтото из кэша, а в базе этой записи ещё нет. Пример неудачной ситуации - сервер A добавил что-то в кэш и умер до обновления базы
        //  Думаю было бы элегантнее унаследовать DbManager от интерфейса с двумя методами, а instance кэша (например https://github.com/ben-manes/caffeine)
        //  сделать полем в этом классе.
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
        return new UrlsBond(url, URLFormatter.createShortenedById(id));
    }

    public UrlsBond expandUrl(Long id) {
        String url = map.rget(id);
        if (url == null)
            throw new RuntimeException("Cannot find shortened url with id " + id);
        return new UrlsBond(map.rget(id), URLFormatter.createShortenedById(id));
    }
}
