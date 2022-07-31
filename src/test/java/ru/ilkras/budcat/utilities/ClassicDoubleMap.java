package ru.ilkras.budcat.utilities;

import java.util.Map;

/**
 * Implementation of DoubleMap using simple maps of your taste :)
 */
public class ClassicDoubleMap<V1, V2> implements DoubleMap<V1, V2> {
    final private Map<V1, V2> map;
    final private Map<V2, V1> rmap;

    final private DoubleMap.OnAdd<V1, V2> onAdd;

    public ClassicDoubleMap(Map<V1, V2> iMap, Map<V2, V1> iRmap, DoubleMap.OnAdd<V1, V2> onAdd) {
        map = iMap;
        rmap = iRmap;
        this.onAdd = onAdd;
    }

    @Override
    public V2 get(V1 key) {
        return map.get(key);
    }

    @Override
    public V1 rget(V2 key) {
        return rmap.get(key);
    }

    @Override
    public void add(V1 o1, V2 o2) {
        if (onAdd != null) {
            onAdd.onAdd(o1, o2);
        }
        map.compute(o1, (k, v) -> {
            rmap.put(o2, o1);
            return o2;
        });
    }
}
