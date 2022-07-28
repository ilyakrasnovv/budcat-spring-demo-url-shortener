package ru.ilkras.budcat.data;

import ru.ilkras.budcat.models.DbUrlsBond;

import java.util.List;

public interface UrlBondsSavingManager {
    /**
     * Function responsible for saving/caching new url bond
     * @param bond bond you want to save
     */
    void addUrlsBond(DbUrlsBond bond);

    /**
     * Function responsible for loading cache from saved files after restart or failure
     * @return all saved bonds
     */
    List<DbUrlsBond> recoverBonds();
}
