package org.hahn.hson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jianghan on 2016/11/23.
 */
public class TestByteChunk {

    ByteChunk chunk;

    @Before
    public void setup() {
        chunk = new ByteChunk();
    }

    @Test
    public void testInt() {
        int p, v;

        p = chunk.write(10);
        v = chunk.getInt(p);
        Assert.assertEquals(10, v);

        p = chunk.write(Integer.MAX_VALUE);
        v = chunk.getInt(p);
        Assert.assertEquals(Integer.MAX_VALUE, v);

        p = chunk.write(Integer.MIN_VALUE);
        v = chunk.getInt(p);
        Assert.assertEquals(Integer.MIN_VALUE, v);
    }

    @Test
    public void testLong() {
        int p;
        long v;

        p = chunk.write(10L);
        v = chunk.getLong(p);
        Assert.assertEquals(10L, v);

        p = chunk.write(Long.MAX_VALUE);
        v = chunk.getLong(p);
        Assert.assertEquals(Long.MAX_VALUE, v);

        p = chunk.write(Long.MIN_VALUE);
        v = chunk.getLong(p);
        Assert.assertEquals(Long.MIN_VALUE, v);
    }

    @Test
    public void testDouble() {
        int p;
        double v;

        p = chunk.write(10.0);
        v = chunk.getDouble(p);
        Assert.assertEquals(10.0, v, 1e-6);

        p = chunk.write(Double.MAX_VALUE);
        v = chunk.getDouble(p);
        Assert.assertEquals(Double.MAX_VALUE, v, 1e-6);

        p = chunk.write(Long.MIN_VALUE);
        v = chunk.getDouble(p);
        Assert.assertEquals(Double.MIN_VALUE, v, 1e-6);
    }

    @Test
    public void testChar() {
        int p;
        char v;

        p = chunk.write('a');
        v = chunk.getChar(p);
        Assert.assertEquals('a', v);

        p = chunk.write(Character.MAX_VALUE);
        v = chunk.getChar(p);
        Assert.assertEquals(Character.MAX_VALUE, v);

        p = chunk.write(Character.MIN_VALUE);
        v = chunk.getChar(p);
        Assert.assertEquals(Character.MIN_VALUE, v);
    }
}
