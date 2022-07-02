package ru.ilkras.budcat.utilities;

import java.util.Map;

public class DoubleMap<V1, V2> {
    @FunctionalInterface
    public interface OnAdd<V1, V2> {
        void onAdd(V1 v1, V2 v2);
    }

    OnAdd<V1, V2> onAdd;

    Map<V1, V2> map;
    Map<V2, V1> reversedMap;

    public DoubleMap(Map<V1, V2> map, Map<V2, V1> reversedMap) {
        this.map = map;
        this.reversedMap = reversedMap;
    }

    public DoubleMap(Map<V1, V2> map, Map<V2, V1> reversedMap, OnAdd onAdd) {
        this.map = map;
        this.reversedMap = reversedMap;
        this.onAdd = onAdd;
    }

    public V2 get(V1 key) {
        return map.get(key);
    }

    public V1 rget(V2 key) {
        return reversedMap.get(key);
    }

    public void add(V1 v1, V2 v2) {
        if (onAdd != null) {
            onAdd.onAdd(v1, v2);
        }
        map.put(v1, v2);
        reversedMap.put(v2, v1);
    }
}
