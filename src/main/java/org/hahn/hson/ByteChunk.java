package org.hahn.hson;

/**
 * Created by jianghan on 2016/11/18.
 */
public class ByteChunk {

    private static final int CHUNK_LENGTH = 5120;
    private int chunkId;
    private byte[] chunk;
    private int wpos;

    public ByteChunk(int chunkId) {
        this.chunkId = chunkId;
        this.chunk = new byte[CHUNK_LENGTH];
        this.wpos = 0;
    }

}
