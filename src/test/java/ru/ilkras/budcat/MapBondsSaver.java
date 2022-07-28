package ru.ilkras.budcat;

import ru.ilkras.budcat.data.UrlBondsSavingManager;
import ru.ilkras.budcat.models.DbUrlsBond;

import java.util.List;

public class MapBondsSaver implements UrlBondsSavingManager {

    @Override
    public void addUrlsBond(DbUrlsBond bond) {

    }

    @Override
    public List<DbUrlsBond> recoverBonds() {
        return null;
    }
}