package org.hahn.hson;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jianghan on 2016/11/9.
 */
public class JSONObject {

    private Map<String, Object> data;
    private boolean onlyRead = true;
    public static final Object NULL = new Null();

    public JSONObject() {
        this.data = new HashMap<>();
    }

    public JSONObject(String source) throws JSONException {
        this(new JSONTokener(source));
    }

    public JSONObject(JSONTokener x) throws JSONException {
        this();
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
        for (; ; ) {
            c = x.nextClean();
            switch (c) {
                case 0:
                    throw x.syntaxError("A JSONObject text must end with '}'");
                case '}':
                    return;
                default:
                    x.back();
                    key = x.nextValue().toString();
            }

            // The key is followed by ':'.

            c = x.nextClean();
            if (c != ':') {
                throw x.syntaxError("Expected a ':' after a key");
            }
            this.putOnce(key, x.nextValue());

            // Pairs are separated by ','.

            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == '}') {
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or '}'");
            }
        }
    }

    /**
     * get api
     **/
    public Object get(String key) throws JSONException {
        if (key == null) {
            throw new JSONException("Null key.");
        } else {
            Object object = this.opt(key);
            if (object == null) {
                throw new JSONException("JSONObject[" + quote(key) + "] not found.");
            } else {
                return object;
            }
        }
    }

    public boolean getBoolean(String key) throws JSONException {
        Object object = this.get(key);
        if (!object.equals(Boolean.FALSE) && (!(object instanceof String) || !((String) object).equalsIgnoreCase("false"))) {
            if (!object.equals(Boolean.TRUE) && (!(object instanceof String) || !((String) object).equalsIgnoreCase("true"))) {
                throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public double getDouble(String key) throws JSONException {
        Object object = this.get(key);

        try {
            return object instanceof Number ? ((Number) object).doubleValue() : Double.parseDouble((String) object);
        } catch (Exception var4) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a number.");
        }
    }

    public int getInt(String key) throws JSONException {
        Object object = this.get(key);

        try {
            return object instanceof Number ? ((Number) object).intValue() : Integer.parseInt((String) object);
        } catch (Exception var4) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not an int.");
        }
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        Object object = this.get(key);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
        }
    }

    public long getLong(String key) throws JSONException {
        Object object = this.get(key);

        try {
            return object instanceof Number ? ((Number) object).longValue() : Long.parseLong((String) object);
        } catch (Exception var4) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a long.");
        }
    }

    public static String[] getNames(JSONObject jo) {
        int length = jo.length();
        if (length == 0) {
            return null;
        } else {
            Iterator iterator = jo.keys();
            String[] names = new String[length];

            for (int i = 0; iterator.hasNext(); ++i) {
                names[i] = (String) iterator.next();
            }

            return names;
        }
    }

    public static String[] getNames(Object object) {
        if (object == null) {
            return null;
        } else {
            Class klass = object.getClass();
            Field[] fields = klass.getFields();
            int length = fields.length;
            if (length == 0) {
                return null;
            } else {
                String[] names = new String[length];

                for (int i = 0; i < length; ++i) {
                    names[i] = fields[i].getName();
                }

                return names;
            }
        }
    }

    public String getString(String key) throws JSONException {
        Object object = this.get(key);
        if (object instanceof String) {
            return (String) object;
        } else {
            throw new JSONException("JSONObject[" + quote(key) + "] not a string.");
        }
    }

    public boolean has(String var1) {
        return this.data.containsKey(var1);
    }

    public boolean isNull(String var1) {
        return NULL.equals(this.opt(var1));
    }

    public Iterator keys() {
        return this.data.keySet().iterator();
    }

    public int length() {
        return this.data.size();
    }

    /**
     * opt api
     **/
    public Object opt(String key) {
        return key == null ? null : this.data.get(key);
    }

    public boolean optBoolean(String var1) {
        return this.optBoolean(var1, false);
    }

    public boolean optBoolean(String var1, boolean var2) {
        try {
            return this.getBoolean(var1);
        } catch (Exception var4) {
            return var2;
        }
    }

    public double optDouble(String var1) {
        return this.optDouble(var1, 0.0D / 0.0);
    }

    public double optDouble(String var1, double var2) {
        try {
            Object var4 = this.opt(var1);
            return var4 instanceof Number ? ((Number) var4).doubleValue() : (new Double((String) var4)).doubleValue();
        } catch (Exception var5) {
            return var2;
        }
    }

    public int optInt(String var1) {
        return this.optInt(var1, 0);
    }

    public int optInt(String var1, int var2) {
        try {
            return this.getInt(var1);
        } catch (Exception var4) {
            return var2;
        }
    }

    public JSONObject optJSONObject(String var1) {
        Object var2 = this.opt(var1);
        return var2 instanceof JSONObject ? (JSONObject) var2 : null;
    }

    public long optLong(String var1) {
        return this.optLong(var1, 0L);
    }

    public long optLong(String var1, long var2) {
        try {
            return this.getLong(var1);
        } catch (Exception var5) {
            return var2;
        }
    }

    public String optString(String var1) {
        return this.optString(var1, "");
    }

    public String optString(String key, String defaultValue) {
        Object var3 = this.opt(key);
        return var3 != null ? var3.toString() : defaultValue;
    }

    /**
     * put api
     **/
    public JSONObject put(String key, Object value) {
        if (key == null) {
            throw new NullPointerException("Null key.");
        }
        if (value != null) {
            testValidity(value);
            this.data.put(key, value);
        } else {
            this.remove(key);
        }
        return this;
    }

    public JSONObject put(String key, boolean value) {
        this.data.put(key, value ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject put(String key, double value) {
        this.data.put(key, value);
        return this;
    }

    public JSONObject put(String key, int value) {
        this.data.put(key, value);
        return this;
    }

    public JSONObject put(String key, long value) {
        this.data.put(key, value);
        return this;
    }

    public JSONObject put(String key, Map<?, ?> value) {
        this.data.put(key, value);
        return this;
    }

    public JSONObject putOnce(String key, Object value) {
        if (key != null && value != null) {
            if (this.opt(key) != null) {
                throw new JSONException("Duplicate key \"" + key + "\"");
            }

            this.put(key, value);
        }
        return this;
    }

    public JSONObject putOpt(String key, Object value) throws JSONException {
        if (key != null && value != null) {
            this.put(key, value);
        }
        return this;
    }

    public Object remove(String key) {
        return this.data.remove(key);
    }

    @Override
    public String toString() {
        return "";
    }

    public String toPrettyString() {
        return data.toString();
    }

    public byte[] toBytes() {
        return new byte[0];
    }

    public static String quote(String string) {
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            String var10000;
            try {
                var10000 = quote(string, sw).toString();
            } catch (IOException var5) {
                return "";
            }

            return var10000;
        }
    }

    public static Writer quote(String string, Writer w) throws IOException {
        if (string != null && string.length() != 0) {
            char c = 0;
            int len = string.length();
            w.write(34);

            for (int i = 0; i < len; ++i) {
                char b = c;
                c = string.charAt(i);
                switch (c) {
                    case '\b':
                        w.write("\\b");
                        continue;
                    case '\t':
                        w.write("\\t");
                        continue;
                    case '\n':
                        w.write("\\n");
                        continue;
                    case '\f':
                        w.write("\\f");
                        continue;
                    case '\r':
                        w.write("\\r");
                        continue;
                    case '\"':
                    case '\\':
                        w.write(92);
                        w.write(c);
                        continue;
                    case '/':
                        if (b == 60) {
                            w.write(92);
                        }

                        w.write(c);
                        continue;
                }

                if (c >= 32 && (c < 128 || c >= 160) && (c < 8192 || c >= 8448)) {
                    w.write(c);
                } else {
                    w.write("\\u");
                    String hhhh = Integer.toHexString(c);
                    w.write("0000", 0, 4 - hhhh.length());
                    w.write(hhhh);
                }
            }

            w.write(34);
            return w;
        } else {
            w.write("\"\"");
            return w;
        }
    }

    static void testValidity(Object var0) throws JSONException {
        if (var0 != null) {
            if (var0 instanceof Double) {
                if (((Double) var0).isInfinite() || ((Double) var0).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            } else if (var0 instanceof Float && (((Float) var0).isInfinite() || ((Float) var0).isNaN())) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }

    }

    public static Object stringToValue(String string) {
        if (string.equals("")) {
            return string;
        }
        if (string.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase("null")) {
            return JSONObject.NULL;
        }

        /**
         * If it might be a number, try converting it. If a number cannot be
         * produced, then the value will just be a string.
         */

        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                if (string.indexOf('.') > -1 || string.indexOf('e') > -1
                        || string.indexOf('E') > -1
                        || "-0".equals(string)) {
                    Double d = Double.valueOf(string);
                    if (!d.isInfinite() && !d.isNaN()) {
                        return d;
                    }
                } else {
                    Long myLong = new Long(string);
                    if (string.equals(myLong.toString())) {
                        if (myLong.longValue() == myLong.intValue()) {
                            return Integer.valueOf(myLong.intValue());
                        }
                        return myLong;
                    }
                }
            } catch (Exception ignore) {
            }
        }
        return string;
    }

    private static final class Null {

        /**
         * There is only intended to be a single instance of the NULL object,
         * so the clone method returns itself.
         *
         * @return NULL.
         */
        @Override
        protected final Object clone() {
            return this;
        }

        /**
         * A Null object is equal to the null value and to itself.
         *
         * @param object An object to test for nullness.
         * @return true if the object parameter is the JSONObject.NULL object or
         * null.
         */
        @Override
        public boolean equals(Object object) {
            return object == null || object == this;
        }

        /**
         * Get the "null" string value.
         *
         * @return The string "null".
         */
        public String toString() {
            return "null";
        }
    }
}
