package org.hahn.hson;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jianghan on 2016/11/24.
 */
public class JSON {

    private ByteChunk chunk;
    private Map<String, JSONMeta> metaMap;

    public JSON(JSONObject obj) {
        this.chunk = new ByteChunk();
        Iterator iter = obj.keys();
        String field;
        JSONMeta meta;
        JType t;
        while (iter.hasNext()) {
            field = (String) iter.next();
            Object value = obj.get(field);
            meta = obj.getMeta(field);
            t = meta.getType();
            switch (t) {
                case Int:
                    meta.setOffset(chunk.write((int) value));
                    break;
                case Long:
                    meta.setOffset(chunk.write((long) value));
                    break;
                case Double:
                    meta.setOffset(chunk.write((Double) value));
                    break;
                case String:
                    String v = (String) value;
                    int len = v.length();
                    meta.setOffset(chunk.write(len));
                    chunk.write(v);
                    break;
                case Tuple2List:
                    break;
                default:
            }
            metaMap.put(field, meta);
        }
    }

    public int getInt(String key) {
        if (key == null) {
            throw new JSONException("Null key.");
        }
        JSONMeta meta = metaMap.get(key);
        if (meta == null) {
            throw new JSONException("key is not in meta.");
        } else if (meta.getType().getClz() != int.class) {
            throw new JSONException("JSONObject[" + key + "] is not an int.");
        } else {
            return chunk.getInt(meta.getOffset());
        }
    }

    public long getLong(String key) throws JSONException {
        if (key == null) {
            throw new JSONException("Null key.");
        }
        JSONMeta meta = metaMap.get(key);
        if (meta == null) {
            throw new JSONException("key is not in meta.");
        } else if (meta.getType().getClz() != long.class) {
            throw new JSONException("JSONObject[" + key + "] is not an int.");
        } else {
            return chunk.getLong(meta.getOffset());
        }
    }

    public double getDouble(String key) {
        if (key == null) {
            throw new JSONException("Null key.");
        }
        JSONMeta meta = metaMap.get(key);
        if (meta == null) {
            throw new JSONException("key is not in meta.");
        } else if (meta.getType().getClz() != double.class) {
            throw new JSONException("JSONObject[" + key + "] is not an int.");
        } else {
            return chunk.getDouble(meta.getOffset());
        }
    }

    public String getString(String key) throws JSONException {
        if (key == null) {
            throw new JSONException("Null key.");
        }
        JSONMeta meta = metaMap.get(key);
        if (meta == null) {
            throw new JSONException("key is not in meta.");
        } else if (meta.getType().getClz() != String.class) {
            throw new JSONException("JSONObject[" + key + "] is not an int.");
        } else {
            int len = chunk.getInt(meta.getOffset());
            return chunk.getString(meta.getOffset() + 4, len);
        }
    }

}
