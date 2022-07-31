package ru.ilkras.budcat.utilities;

/**
 * Interface for double-side storage (both key and value are keys)
 * @param <V1> first-side key type (key for usual map)
 * @param <V2> second-side key type (key for reversed map)
 */
public interface DoubleMap<V1, V2> {
    @FunctionalInterface
    interface OnAdd<V1, V2> {
        void onAdd(V1 v1, V2 v2);
    }

    OnAdd onAdd = null;

    /**
     * Get by V1.
     * @param key key
     * @return corresponding value in map for the key
     */
    V2 get(V1 key);

    /**
     * Get by V2.
     * @param key key
     * @return corresponding value in reversed map for the key
     */
    V1 rget(V2 key);

    /**
     * add ((key; value)) (v1, v2) to map and (v2, v1) to reversed map
     * @param v1 v1
     * @param v2 v2
     */
    void add(V1 v1, V2 v2);
}