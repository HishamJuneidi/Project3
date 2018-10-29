import java.nio.ByteBuffer;

/**
 * 
 * @author risha97, hishamj6
 * @version 10/29/2018
 */
public class MyBlock {
    private byte[] pool;
    private boolean dirtyBit;
    private int index;
    private static final int BLOCK_SIZE = 4096;
    
    /**
     * constructor for myBlock
     * @param p byte array storing records
     * @param i location in file of block
     */
    public MyBlock(byte[] p, int i) {
        if (p != null) {
            this.pool = p;
        }
        else {
            this.pool = new byte[BLOCK_SIZE];
        }
        this.index = i;
        this.dirtyBit = false;
    }
    
    /**
     * gets key at index
     * @param i index of file
     * @return key at index
     */
    public short key(int i) {
        short output = 0;
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.put(pool, i, 2);
        output = bb.getShort(0);
        return output;
    }
    
    /**
     * gets the pool in myBlock
     * @return pool
     */
    public byte[] pool() {
        return this.pool;
    }
    
    /**
     * sets the elements in pool
     * @param p new value of pool
     */
    public void setPool(byte[] p) {
        this.pool = p;
    }
    
    /**
     * returns dirty bit val
     * @return whether dirty bit is true
     */
    public boolean dirtyBit() {
        return this.dirtyBit;
    }
    
    /**
     * sets dirty bit val
     * @param b new val
     */
    public void setDirtyBit(boolean b) {
        this.dirtyBit = b;
    }
    
    /**
     * sets the index of block
     * @param i new index of block
     */
    public void setIndex(int i) {
        this.index = i;
    }
    
    /**
     * gets index of block
     * @return index of block
     */
    public int index() {
        return this.index;
    }
}
