package org.hahn.hson;

import java.util.*;

/**
 * Created by jianghan on 2016/11/16.
 * <p>
 * 使用跳表专门支持 (k, v) 形式的 JSONObject
 * sort by value
 */
public class JSONSkipListMap {

    private static final int KEY_NEXT = 0;
    private static final int VALUE_NEXT = 1;
    private Cell[] head = null;
    private int level;
    private int length;

    private int calcLevel(int s) {
        int l = 1;
        while ((1 << l) < s) l++;
        return l;
    }

    private void init(Map<String, Double> map) {
        List<Cell> data = new ArrayList<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            data.add(new Cell(level, entry.getKey(), entry.getValue()));
        }
        Collections.sort(data, new Cell.ValueComparator());
        init(data, VALUE_NEXT);
        Collections.sort(data, new Cell.KeyComparator());
        init(data, KEY_NEXT);
    }

    private void init(List<Cell> data, int t) {
        Cell head = data.get(0);
        Cell[] pre = new Cell[head.next[t].length];
        int[] cnt = new int[pre.length];
        Arrays.fill(pre, head);
        Arrays.fill(cnt, 0);
        for (int i = 1; i < data.size(); ++i) {
            Cell c = data.get(i);
            for (int j = 0; j < level; ++j) {
                ++cnt[j];
                if (cnt[j] % (1 << j) == 0) {
                    pre[j].next[t][j] = c;
                    pre[j] = c;
                    cnt[j] = 0;
                }
            }
        }
        this.head[t] = head;
    }

    public JSONSkipListMap(Map<String, Double> map) {
        if (map == null) {
            throw new JSONException("Initialize data is null.");
        }
        length = map.size();
        level = calcLevel(length / 4) + 1;
        head = new Cell[2];
        init(map);
    }

    public void add(String key, double value) {
        Cell p = find(key, head[KEY_NEXT]);
        if (p.key.equals(key)) {
            return;
        } else {
            Cell np = new Cell(1, key, value);
        }
    }

    public void prettyPrint() {
        Cell point = head[VALUE_NEXT];
        for (int i = 0; i < length(); i++) {
            System.out.println(point);
            point = point.next[VALUE_NEXT][0];
        }
    }

    public void prettyPrint(int t) {
        Cell point = head[t];
        for (int i = 0; i < length(); i++) {
            System.out.println(point);
            point = point.next[t][0];
        }
    }

    public int length() {
        return this.length;
    }

    public double opt(String key, double defaultValue) {
        Cell p = find(key, head[KEY_NEXT]);
        if (p == null || !p.key.equals(key)) {
            return defaultValue;
        }
        return p.val;
    }

    private Cell find(String key, Cell point) {
        if (point == null) {
            return null;
        }
        if (key.equals(point.key)) {
            return point;
        } else {
            int idx = -1;
            for (int i = 0; i < point.next.length; ++i) {
                if (point.next[KEY_NEXT][i].key.compareTo(key) <= 0) {
                    idx = i;
                } else {
                    break;
                }
            }
            if (idx == -1) {
                return point;
            } else {
                return find(key, point.next[KEY_NEXT][idx]);
            }
        }
    }

    public static class Cell {
        private Cell[][] next = null;
        private String key;
        private double val;

        public Cell(int level, String key, double val) {
            this.next = new Cell[2][level];
            this.key = key;
            this.val = val;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[key=").append(key);
            sb.append(",value=").append(val);
            sb.append(",knext=");
            for (Cell c : next[KEY_NEXT]) {
                if (c != null) {
                    sb.append(c.key).append(",");
                } else {
                    sb.append("null,");
                }
            }
            sb.append("vnext=");
            for (Cell c : next[VALUE_NEXT]) {
                if (c != null) {
                    sb.append(c.key).append(",");
                } else {
                    sb.append("null,");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        static class KeyComparator implements Comparator<Cell> {
            @Override
            public int compare(Cell o1, Cell o2) {
                String s1 = o1.key;
                String s2 = o2.key;
                return s1.compareTo(s2);
            }
        }

        static class ValueComparator implements Comparator<Cell> {
            @Override
            public int compare(Cell o1, Cell o2) {
                double s1 = o1.val;
                double s2 = o2.val;
                return (s1 == s2) ? 0 : ((s1 < s2) ? (-1) : (1));
            }
        }
    }

}
