package ru.ilkras.budcat.models;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

// FIXME: Надо форсить проверки что id и origin не null - через расстановку org.jetbrains.annotations.NotNull и runtime проверки в конструкторе и сеттерах.
public class DbUrlsBond {
    private String origin;
    private Long id;

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JdbiConstructor
    public DbUrlsBond() {

    }

    @JdbiConstructor
    public DbUrlsBond(@ColumnName("ORIGIN") String origin, @ColumnName("ID") Long id) {
        this.origin = origin;
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public Long getId() {
        return id;
    }
}
