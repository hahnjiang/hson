package org.hahn.hson;

import java.util.Map;

/**
 * Created by jianghan on 2016/11/9.
 */
public class JSONObject {

    private Map<String, Object> data;

    public static JSONObject parse(String line) {
        if (line == null) {
            return null;
        }
        JSONTokenizer tokenizer = new JSONTokenizer();
        return tokenizer.tokenize(line);
    }

    @Override
    public String toString() {
        return "";
    }

    public String toPrettyString() {
        return "";
    }

}
