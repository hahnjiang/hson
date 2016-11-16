package org.hahn.hson;

import org.junit.Test;

/**
 * Created by jianghan on 2016/11/13.
 */
public class TestJSONObject {

    @Test
    public void testJSON1() {
        String line = "{\"key1\":\"value1\"}";
        JSONObject obj = new JSONObject(line);
        System.out.println(obj.toPrettyString());
    }

}
