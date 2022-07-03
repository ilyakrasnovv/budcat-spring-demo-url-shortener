package ru.ilkras.budcat.models;


import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

public class DbUrlsBond {
    private String origin;
    private Long id;

    @JdbiConstructor
    public DbUrlsBond(@ColumnName("ORIGIN") String origin, @ColumnName("ID") Long shortened) {
        this.origin = origin;
        this.id = shortened;
    }

    public String getOrigin() {
        return origin;
    }

    public Long getId() {
        return id;
    }
}