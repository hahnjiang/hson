package org.hahn.hson;


import org.hahn.hson.structure.JSONSkipListMap;

/**
 * Created by jianghan on 2016/11/23.
 */
public enum JType {
    Int("I", "int", int.class, 4),
    Long("L", "long", long.class, 8),
    Double("D", "double", double.class, 8),
    String("S", "string", String.class, 4),
    Tuple2List("T", "map", JSONSkipListMap.class, 4);

    private String schemaSign;
    private String type;
    private Class clz;
    private int valueLen;

    JType(String schemaSign, String type, Class clz, int valueLen) {
        this.schemaSign = schemaSign;
        this.type = type;
        this.clz = clz;
        this.valueLen = valueLen;
    }

    public static JType parse(String type) {
        for (JType t : values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid JType: ".concat(type));
    }

    public static JType parseFromSign(String sign) {
        for (JType t : values()) {
            if (t.getSchemaSign().equalsIgnoreCase(sign)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid JType: ".concat(sign));
    }

    public String getSchemaSign() {
        return schemaSign;
    }

    public Class getClz() {
        return clz;
    }

    public int getValueLen() {
        return valueLen;
    }

    public java.lang.String getType() {
        return type;
    }

}
