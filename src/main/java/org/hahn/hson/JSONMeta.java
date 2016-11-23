package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/23.
 */
public class JSONMeta {

    private String field;
    private JType type;
    private int offset;

    public JSONMeta(String field, JType type) {
        this.field = field;
        this.type = type;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getField() {
        return field;
    }

    public JType getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

}
