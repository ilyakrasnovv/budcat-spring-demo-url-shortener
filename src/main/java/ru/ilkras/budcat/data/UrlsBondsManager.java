package ru.ilkras.budcat.data;

import ru.ilkras.budcat.models.DbUrlsBond;
import ru.ilkras.budcat.models.UrlsBond;
import ru.ilkras.budcat.utilities.DoubleMap;

import java.util.HashMap;

import static java.lang.Long.max;

public class UrlsBondsManager {

    // FIXME: Поля класса лучше делать приватными.

    DbManager db; // InterfaceDbManager db
    DoubleMap<String, Long> map;
    Long maxId = -1L;

    public UrlsBondsManager(/* InterfaceDbManager db */) {
        db = new DbManager();
        HashMap<String, Long> recoveredFromDB = new HashMap<>();
        HashMap<Long, String> recoveredFromDBReversed = new HashMap<>();
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
        return new UrlsBond(url, id);
    }

    public UrlsBond expandUrl(Long id) {
        String url = map.rget(id);
        if (url == null)
            throw new RuntimeException("Cannot find shortened url with id " + id);
        return new UrlsBond(map.rget(id), id);
    }
}
