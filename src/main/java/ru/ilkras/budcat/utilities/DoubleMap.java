package ru.ilkras.budcat.utilities;

import com.github.benmanes.caffeine.cache.Cache;

public class DoubleMap<V1, V2> {
    @FunctionalInterface
    public interface OnAdd<V1, V2> {
        void onAdd(V1 v1, V2 v2);
    }

    private OnAdd<V1, V2> onAdd;

    private Cache<V1, V2> map;
    private Cache<V2, V1> reversedMap;

    public DoubleMap(Cache<V1, V2> map, Cache<V2, V1> reversedMap) {
        this.map = map;
        this.reversedMap = reversedMap;
    }

    public DoubleMap(Cache<V1, V2> map, Cache<V2, V1> reversedMap, OnAdd onAdd) {
        this.map = map;
        this.reversedMap = reversedMap;
        this.onAdd = onAdd;
    }

    public V2 get(V1 key) {
        return map.getIfPresent(key);
    }

    public V1 rget(V2 key) {
        return reversedMap.getIfPresent(key);
    }

    public void add(V1 v1, V2 v2) {
        if (onAdd != null) {
            onAdd.onAdd(v1, v2);
        }
        map.asMap().compute(v1, (v11, v22) -> {
            reversedMap.put(v2, v1);
            return v2;
        });
    }
}
