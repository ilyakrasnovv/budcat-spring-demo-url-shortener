package ru.ilkras.budcat.utilities;

import ru.ilkras.budcat.data.DuplicatesFoundException;
import ru.ilkras.budcat.data.UrlBondsSavingManager;
import ru.ilkras.budcat.models.DbUrlsBond;

import java.util.List;

/**
 * // TODO: 28.07.2022
 * to implement
 */
public class MapBondsSaver implements UrlBondsSavingManager {
    @Override
    public void addUrlsBond(DbUrlsBond bond) {

    }

    @Override
    public List<DbUrlsBond> recoverBonds() {
        return null;
    }

    @Override
    public DbUrlsBond loadBondById(Long id) throws DuplicatesFoundException {
        return null;
    }

    @Override
    public DbUrlsBond loadBondByOrigin(String origin) throws DuplicatesFoundException {
        return null;
    }
}