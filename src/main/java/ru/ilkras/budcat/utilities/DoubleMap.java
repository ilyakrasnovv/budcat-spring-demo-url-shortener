package ru.ilkras.budcat.utilities;

import com.github.benmanes.caffeine.cache.Cache;

public interface DoubleMap<V1, V2> {
    V2 get(V1 key);

    V1 rget(V2 key);

    void add(V1 v1, V2 v2);
}