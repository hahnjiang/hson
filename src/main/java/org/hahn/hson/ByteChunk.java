package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/18.
 */
public class ByteChunk {

    private static final int CHUNK_LENGTH = 65536;
    private byte[] chunk;
    private int wpos;
    private ByteChunk next;

    public ByteChunk() {
        this.chunk = new byte[CHUNK_LENGTH];
        this.wpos = 0;
    }

    public ByteChunk(byte[] chunk) {
        this.chunk = chunk;
        this.wpos = chunk.length;
    }

    public int write(byte x) {
        int r = 0;
        ByteChunk point = this;
        while (point.next != null) {
            point = point.next;
            r += CHUNK_LENGTH;
        }
        point.chunk[wpos] = x;
        point.wpos++;
        if (point.wpos == CHUNK_LENGTH) {
            point.next = new ByteChunk();
        }
        return r + point.wpos - 1;
    }

    public int write(int x) {
        int r = write((byte) (x >>> 24));
        write((byte) (x >>> 16));
        write((byte) (x >>> 8));
        write((byte) (x));
        return r;
    }

    public int write(long x) {
        int r = write((byte) (x >> 56));
        write((byte) (x >> 48));
        write((byte) (x >> 40));
        write((byte) (x >> 32));
        write((byte) (x >> 24));
        write((byte) (x >> 16));
        write((byte) (x >> 8));
        write((byte) (x));
        return r;
    }

    public int write(double x) {
        long l = Double.doubleToRawLongBits(x);
        return write(l);
    }

    public int write(char x) {
        int r = write((byte) (x >> 8));
        write((byte) (x));
        return r;
    }

    public int write(String str) {
        int r = -1; // TODO
        for (int i = 0; i < str.length(); i++) {
            r = write(str.charAt(i));
        }
        return r;
    }

    public byte getByte(int pos) {
        ByteChunk point = this;
        while (point.wpos <= pos) {
            pos -= point.wpos;
            point = point.next;
        }
        return point.chunk[pos];
    }

    public int getInt(int pos) {
        return ((getByte(pos) & 0xff) << 24)
                | ((getByte(pos + 1) & 0xff) << 16)
                | ((getByte(pos + 2) & 0xff) << 8)
                | ((getByte(pos + 3) & 0xff));
    }

    public long getLong(int pos) {
        return (((long) getByte(pos) & 0xff) << 56)
                | (((long) getByte(pos + 1) & 0xff) << 48)
                | (((long) getByte(pos + 2) & 0xff) << 40)
                | (((long) getByte(pos + 3) & 0xff) << 32)
                | (((long) getByte(pos + 4) & 0xff) << 24)
                | (((long) getByte(pos + 5) & 0xff) << 16)
                | (((long) getByte(pos + 6) & 0xff) << 8)
                | (((long) getByte(pos + 7) & 0xff));
    }

    public double getDouble(int pos) {
        long l = getLong(pos);
        return Double.longBitsToDouble(l);
    }

    public char getChar(int pos) {
        return (char) ((((char) getByte(pos) & 0xff) << 8)
                | (((char) getByte(pos + 1) & 0xff)));

    }

    public String getString(int pos, int length) {
        char[] chs = new char[length];
        for (int i = 0; i < length; ++i) {
            chs[i] = getChar(pos + i);
        }
        return new String(chs);
    }

    public int size() {
        int r = 0;
        ByteChunk point = this;
        while (point.next != null) {
            point = point.next;
            r += CHUNK_LENGTH;
        }
        return r + point.wpos;
    }

}
