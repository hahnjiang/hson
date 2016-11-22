package org.hahn.hson;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianghan on 2016/11/21.
 */
public class TestJSONSkipListMap {

    @Test
    public void test1() {
        Map<String, Double> map = createMap(100);
        JSONSkipListMap json = new JSONSkipListMap(map);
        json.prettyPrint(0);
    }

    private Map<String, Double> createMap(int size) {
        Map<String, Double> result = new HashMap<>();
        for (int i = 0; i < size; i++) {
            result.put(String.valueOf(i), (double) size - i);
        }
        return result;
    }

}
