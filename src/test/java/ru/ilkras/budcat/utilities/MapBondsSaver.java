package ru.ilkras.budcat.utilities;

import ru.ilkras.budcat.data.DuplicatesFoundException;
import ru.ilkras.budcat.data.UrlBondsSavingManager;
import ru.ilkras.budcat.models.DbUrlsBond;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * // TODO: 28.07.2022
 * to implement
 */
public class MapBondsSaver implements UrlBondsSavingManager {
    List<DbUrlsBond> bondsList = new ArrayList<>();
    ClassicDoubleMap<String, Long> saver = new ClassicDoubleMap<>(
            new HashMap<>(),
            new HashMap<>(),
            (String s, Long i)
                    -> bondsList.add(new DbUrlsBond(s, i))
    );

    @Override
    public void addUrlsBond(DbUrlsBond bond) {
        bondsList.add(bond);
        saver.add(bond.getOrigin(), bond.getId());
    }

    @Override
    public List<DbUrlsBond> recoverBonds() {
        return bondsList;
    }

    @Override
    public DbUrlsBond loadBondById(Long id) {
        if (saver.rget(id) == null)
            return null;
        return new DbUrlsBond(saver.rget(id), id);
    }

    @Override
    public DbUrlsBond loadBondByOrigin(String origin)  {
        if (saver.get(origin) == null)
            return null;
        return new DbUrlsBond(origin, saver.get(origin));
    }
}