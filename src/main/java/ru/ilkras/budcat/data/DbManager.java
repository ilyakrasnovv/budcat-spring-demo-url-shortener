package ru.ilkras.budcat.data;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import ru.ilkras.budcat.BudcatApplication;
import ru.ilkras.budcat.models.DbUrlsBond;

import java.util.List;

// FIXME: Для тестирования удобнее что бы этот класс наследовался от интерфейса с двумя методами. Тогда в тесте можно будет имплементацию чем-то подменить не затрагивая менеджер.
//    interface InterfaceDbManager {
//        void addUrlsBond(DbUrlsBond bond);
//        List<DbUrlsBond> recoverBonds();
//    }
public class DbManager {
    Jdbi jdbi = Jdbi.create(BudcatApplication.NOE.getDatabasePath());

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
}
