package ru.ilkras.budcat.data;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import ru.ilkras.budcat.BudcatApplication;
import ru.ilkras.budcat.models.DbUrlsBond;

import java.util.List;

public class DbManager implements UrlBondsSavingManager {
    private final Jdbi jdbi = Jdbi.create(BudcatApplication.NOE.getDatabasePath());

    public DbManager() {
        jdbi.setSqlLogger(new Slf4JSqlLogger());
        jdbi.useHandle(handle -> handle.execute(
                "CREATE TABLE IF NOT EXISTS URLSBONDS " +
                        "(ORIGIN VARCHAR, ID LONG PRIMARY KEY)"
        ));
    }

    public void addUrlsBond(DbUrlsBond bond) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO URLSBONDS (ORIGIN, ID) VALUES (:ORIGIN, :ID )")
                        .bind("ORIGIN", bond.getOrigin())
                        .bind("ID", bond.getId())
                        .execute()
        );
    }

    public List<DbUrlsBond> recoverBonds() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM URLSBONDS ORDER BY ID")
                        .mapToBean(DbUrlsBond.class)
                        .list()
        );
    }

    @Override
    public DbUrlsBond loadBondById(Long id) throws DuplicatesFoundException {
        List<DbUrlsBond> response = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM URLSBONDS WHERE ID=:ID")
                        .bind("ID", id)
                        .mapToBean(DbUrlsBond.class)
                        .list()
        );
        if (response.isEmpty())
            return null;
        if (response.size() > 1)
            throw new DuplicatesFoundException(
                    "FOUND SEVERAL BONDS WITH THE SAME ID (" + id + ") IN THE DATABASE");
        return response.get(0);
    }
    @Override
    public DbUrlsBond loadBondByOrigin(String origin) throws DuplicatesFoundException {
        List<DbUrlsBond> response = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM URLSBONDS WHERE ORIGIN=:ORIGIN")
                        .bind("ORIGIN", origin)
                        .mapToBean(DbUrlsBond.class)
                        .list()
        );
        if (response.isEmpty())
            return null;
        if (response.size() > 1)
            throw new DuplicatesFoundException(
                    "FOUND SEVERAL BONDS WITH THE SAME ORIGIN (" + origin + ") IN THE DATABASE");
        return response.get(0);
    }
}
