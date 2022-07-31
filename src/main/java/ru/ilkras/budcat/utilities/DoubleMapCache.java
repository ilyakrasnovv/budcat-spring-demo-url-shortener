package ru.ilkras.budcat.utilities;

import com.github.benmanes.caffeine.cache.Cache;

public class DoubleMapCache<V1, V2> implements DoubleMap<V1, V2> {
    private DoubleMap.OnAdd<V1, V2> onAdd;
    final private Cache<V1, V2> map;
    final private Cache<V2, V1> reversedMap;

    public DoubleMapCache(Cache<V1, V2> map, Cache<V2, V1> reversedMap) {
        this.map = map;
        this.reversedMap = reversedMap;
    }

    public DoubleMapCache(Cache<V1, V2> map, Cache<V2, V1> reversedMap, DoubleMap.OnAdd<V1, V2> onAdd) {
        this.map = map;
        this.reversedMap = reversedMap;
        this.onAdd = onAdd;
    }

    @Override
    public V2 get(V1 key) {
        return map.getIfPresent(key);
    }

    @Override
    public V1 rget(V2 key) {
        return reversedMap.getIfPresent(key);
    }

    @Override
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
