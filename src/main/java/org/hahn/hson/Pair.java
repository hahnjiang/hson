package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/21.
 */
public class Pair<T1, T2> {

    private T1 key;
    private T2 value;

    public Pair() {
    }

    public Pair(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    public T1 getKey() {
        return key;
    }

    public T2 getValue() {
        return value;
    }

}

