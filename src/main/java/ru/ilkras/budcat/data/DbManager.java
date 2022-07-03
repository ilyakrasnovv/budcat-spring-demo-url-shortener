package ru.ilkras.budcat.data;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;
import ru.ilkras.budcat.BudcatApplication;
import ru.ilkras.budcat.models.DbUrlsBond;
import ru.ilkras.budcat.models.UrlsBond;

import java.util.List;

public class DbManager {
    Jdbi jdbi = Jdbi.create(BudcatApplication.NOE.databasePath);

    public DbManager() {
        jdbi.useHandle(handle -> handle.execute(
                "create table if not exists URLSBONDS " +
                        "(ORIGIN VARCHAR, ID LONG PRIMARY KEY)"
        ));
    }

    public void addUrlsBond(DbUrlsBond bond) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO URLSBONDS (ORIGIN, ID) VALUES (:ORIGIN, :SHORTENED )")
                        .bindBean(bond)
        );
    }

    public List<DbUrlsBond> recoverBonds() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM URLSBONDS ORDER BY ID")
                        .mapToBean(DbUrlsBond.class)
                        .list()
        );
    }
}