import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * buffer pool that serves as go between of file
 * and sorting algorithm
 * @author risha97, hishamj6
 * @version 10/29/2018
 */
public class BufferPool {
    private RandomAccessFile file;
    private MyQueue lruCache;
    private boolean cacheFlag;
    private static final int BLOCK_SIZE = 4096;
    private static final int RECORD_SIZE = 4;
    private int cacheHits = 0;
    private int reads = 0;
    private int writes = 0;
    
    /**
     * constructor for buffer pool
     * @param f file being read and written
     * @param numBuffers number of buffers in pool
     * @throws IOException
     */
    public BufferPool(RandomAccessFile f, int numBuffers) throws IOException {
        this.file = f;
        this.lruCache = new MyQueue(numBuffers);
        int i;
        for (i = 0; i < numBuffers; i++) {
            this.lruCache.add(new MyBlock(null, -1));
        }
        
    }
    
    /**
     * removes block from buffer pool
     * @return block that has been removed
     * @throws IOException
     */
    public MyBlock remove() throws IOException {
        MyBlock block = this.lruCache.remove();
        if (block != null && block.dirtyBit()) {
            this.file.seek(block.index() * BLOCK_SIZE);
            byte[] temp = block.pool();
            this.file.write(temp);
            this.writes++;
            this.file.seek(0);
        }
        return block;
    }
    
    /**
     * retrieves block at a given index
     * @param index index at file of block
     * @return block at the index
     * @throws IOException
     */
    public MyBlock getBlock(int index) throws IOException {
        int offset = (index * RECORD_SIZE) / BLOCK_SIZE;
        MyBlock output = this.lruCache.search(offset);
        if (output == null) {
            if (lruCache.size() == lruCache.capacity()) {
                output = this.remove();
                output.setIndex(offset);
                output.setDirtyBit(false);
            }
            else {
                output = new MyBlock(null, offset);
            }
            this.file.seek(BLOCK_SIZE * offset);
            this.file.read(output.pool(), 0, BLOCK_SIZE);
            this.file.seek(0);
            this.lruCache.add(output);
            this.reads += 1;
            this.cacheFlag = false;
            return output;
        }
        // if in cache, increment cache hits
        this.cacheFlag = true;
        return output;
    }
    
    /**
     * gets key at given index
     * @param index index at file of key
     * @return key at given index
     * @throws IOException
     */
    public short key(int index) throws IOException {
        short output = 0;
        if (this.cacheFlag) {
            this.cacheHits += 1;
        }
        MyBlock b = this.getBlock(index);
        int offset = (index * RECORD_SIZE) % BLOCK_SIZE;
        output = b.key(offset);
        return output;
    }
    
    /**
     * inserts record at disc
     * @param bytes record being inserted
     * @param size size of record
     * @param index index at block where record is inserted
     * @throws IOException
     */
    public void insert(byte[] bytes, int size, int index) throws IOException {
        MyBlock output = this.getBlock(index);
        int bIndex = (index * RECORD_SIZE) % BLOCK_SIZE;
        byte[] temp = output.pool();
        int i;
        for (i = 0; i < size; i++) {
            temp[bIndex] = bytes[i];
            bIndex++;
        }
        output.setPool(temp);
        output.setDirtyBit(true);
    }
    
    /**
     * gets record at given index 
     * @param index index of file
     * @return record at index
     * @throws IOException
     */
    public byte[] getBytes(int index) throws IOException {
        MyBlock output = this.getBlock(index);
        byte[] bytes = new byte[RECORD_SIZE];
        int bIndex = (index * RECORD_SIZE) % BLOCK_SIZE;
        byte[] temp = output.pool();
        int i;
        for (i = 0; i < RECORD_SIZE; i++) {
            bytes[i] = temp[bIndex];
            bIndex++;
        }
        return bytes;
    }
    
    /**
     * writes everything to file at end
     * @throws IOException
     */
    public void writeAtEnd() throws IOException {
        while (this.lruCache.size() > 0) {
            this.remove();
        }
    }
    
    /**
     * return how many times record being searched
     * for was in cache
     * @return number of cache hits
     */
    public int hits() {
        return this.cacheHits;
    }
    
    /**
     * number of times file was read directly
     * @return number of file reads
     */
    public int reads() {
        return this.reads;
    }
    
    /**
     * how many writes to file were done
     * @return number of writes
     */
    public int writes() {
        return this.writes;
    }
    
    /**
     * returns queue storing blocks
     * @return queue storing blocks
     */
    public MyQueue cache() {
        return this.lruCache;
    }
    
    /**
     * closes file at end of operations
     * @throws IOException
     */
    public void closeFile() throws IOException {
        this.file.close();
    }
}
