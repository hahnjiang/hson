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
    private Cell head = null;
    private int level;
    private int length;

    private int calcLevel(int s) {
        int l = 1;
        while ((1 << l) < s) l++;
        return l;
    }

    private Cell init(Map<String, Double> map) {
        List<Pair<String, Double>> data = new ArrayList<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            data.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        Collections.sort(data, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
                double s1 = o1.getValue();
                double s2 = o2.getValue();
                return (s1 == s2) ? 0 : ((s1 < s2) ? (-1) : (1));
            }
        });
        head = new Cell(level, data.get(0).getKey(), data.get(0).getValue());
        Cell[] pre = new Cell[head.next.length];
        int[] cnt = new int[pre.length];
        Arrays.fill(pre, head);
        Arrays.fill(cnt, 0);
        for (int i = 1; i < data.size(); ++i) {
            Cell c = new Cell(level, data.get(i).getKey(), data.get(i).getValue());
            for (int j = 0; j < level; ++j) {
                ++cnt[j];
                if (cnt[j] % (1 << j) == 0) {
                    pre[j].setNext(j, c);
                    pre[j] = c;
                    cnt[j] = 0;
                }
            }
        }
        return head;
    }

    private Cell init(List<Pair<String, Double>> data, int t) {
        Cell[] pre = new Cell[head.next.length];
        int[] cnt = new int[pre.length];
        Arrays.fill(pre, head);
        Arrays.fill(cnt, 0);
        for (int i = 1; i < data.size(); ++i) {
            Cell c = new Cell(level, data.get(i).getKey(), data.get(i).getValue());
            for (int j = 0; j < level; ++j) {
                ++cnt[j];
                if (cnt[j] % (1 << j) == 0) {
                    pre[j].next[t][j] = c;
                    pre[j] = c;
                    cnt[j] = 0;
                }
            }
        }
    }

    public JSONSkipListMap(Map<String, Double> map) {
        if (map == null) {
            throw new JSONException("Initialize data is null.");
        }
        length = map.size();
        level = calcLevel(length / 4) + 1;
        head = init(map);
    }

    public void add(String key, double value) {
        Cell p = find(key, head);
        if (p.key.equals(key)) {
            return;
        } else {
            Cell np = new Cell(1, key, value);
            np.setNext(0, p.next[0]);
            p.next[0] = np;
        }
    }

    public void prettyPrint() {
        Cell point = head;
        for (int i = 0; i < length(); i++) {
            System.out.println(point);
            point = point.next[0];
        }
    }

    public int length() {
        return this.length;
    }

    public double opt(String key, double defaultValue) {
        Cell p = find(key, head);
        if (p == null || !p.key.equals(key)) {
            return defaultValue;
        }
        return p.val;
    }


    private Cell findByValue(double val, Cell point) {

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
                if (point.next[i].key.compareTo(key) <= 0) {
                    idx = i;
                } else {
                    break;
                }
            }
            if (idx == -1) {
                return point;
            } else {
                return find(key, point.next[idx]);
            }
        }
    }

    public static class Cell {
        private Cell[][] next = null;
        private String key;
        private double val;

        /**
         * public Cell(String key, double val) {
         * this.key = key;
         * this.val = val;
         * }
         */

        public Cell(int level, String key, double val) {
            this.next = new Cell[2][level];
            this.key = key;
            this.val = val;
        }

        protected Cell getKeyNext(int idx) {
            if (idx >= this.next.length) return null;
            return this.next[KEY_NEXT][idx];
        }

        protected Cell getValueNext(int idx) {
            if (idx >= this.next.length) return null;
            return this.next[VALUE_NEXT][idx];
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[key=");
            sb.append(key);
            sb.append(",value=");
            sb.append(val);
            sb.append(",knext=");
            for (Cell c : next[KEY_NEXT]) {
                if (c != null) {
                    sb.append(c.key);
                    sb.append(",");
                } else {
                    sb.append("null,");
                }
            }
            sb.append("vnext=");
            for (Cell c : next[VALUE_NEXT]) {
                if (c != null) {
                    sb.append(c.key);
                    sb.append(",");
                } else {
                    sb.append("null,");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }


}
