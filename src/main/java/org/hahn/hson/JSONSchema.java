package org.hahn.hson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianghan on 2016/11/9.
 * <p>
 * field1:I/L/D/S/TupleList(S:D)
 */
public class JSONSchema {

    public enum ValueType {

        INT("I", 0),
        STRING("S", 1),
        DOUBLE("D", 2),
        LONG("L", 3),
        TUPLE_LIST("T", 4);

        private int val;
        private String type;

        ValueType(String type, int val) {
            this.type = type;
            this.val = val;
        }

        public static ValueType getBySign(int val) {
            for (ValueType t : values()) {
                if (t.val == val) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Can't find ValueType by sign: " + val);
        }

        public static ValueType getByType(String type) {
            for (ValueType t : values()) {
                if (t.type == type) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Can't find ValueType by type: " + type);
        }
    }

    private Map<String, ValueType> schema;

    public JSONSchema(String line) {
        String[] cols = line.split(",");
        schema = new HashMap<>(cols.length);
        for (String col : cols) {
            String[] s = col.split(":", 2);
            schema.put(s[0], ValueType.getByType(s[1]));
        }
    }

    public ValueType getType(String fieldName) {
        return schema.get(fieldName);
    }

}
