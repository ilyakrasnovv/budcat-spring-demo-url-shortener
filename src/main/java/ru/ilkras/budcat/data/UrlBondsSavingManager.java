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

    /**
     * Gets bond from saved with specified id
     * @param id parameter to search
     * @return found bond or null if not found
     * @throws DuplicatesFoundException in case if there is more than 1 bond with specified parameter (as a result of implementation errors in addUrlsBond or recoverBonds)
     */
    DbUrlsBond loadBondById(Long id) throws DuplicatesFoundException;

    /**
     *
     * @param origin parameter to search
     * @return found bond or null if not found
     * @throws DuplicatesFoundException in case if there is more than 1 bond with specified parameter (as a result of implementation errors in addUrlsBond or recoverBonds)
     */
    DbUrlsBond loadBondByOrigin(String origin) throws DuplicatesFoundException;
}
