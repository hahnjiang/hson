package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/16.
 * <p>
 * 使用跳表专门支持 (k, v) 形式的 JSONObject
 */
public class JSONSkipListMap {

    public void add(Cell c) {

    }

    public Cell get(String key) {
       return null;
    }

    public static class Cell {
        private Cell[] next = null;
        private String key;
        private double val;

        public Cell(int level, String key, double val) {
            this.next = new Cell[level];
            this.key = key;
            this.val = val;
        }

        protected void setNext(int idx, Cell c) {
            this.next[idx] = c;
        }

        protected Cell getNext(int idx) {
            if (idx >= this.next.length) return null;
            return this.next[idx];
        }
    }


}
