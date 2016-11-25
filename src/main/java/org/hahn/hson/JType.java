package org.hahn.hson;

import org.hahn.hson.structure.JSONSkipListMap;

/**
 * Created by jianghan on 2016/11/23.
 */
public enum JType {
    Int("I", int.class, 4),
    Long("L", long.class, 8),
    Double("D", double.class, 8),
    String("S", String.class, 4),
    Tuple2List("T", JSONSkipListMap.class, 4);

    private String schemaSign;
    private Class clz;
    private int valueLen;

    JType(String schemaSign, Class clz, int valueLen) {
        this.schemaSign = schemaSign;
        this.clz = clz;
        this.valueLen = valueLen;
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

}
