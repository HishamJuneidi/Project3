import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;


public class BufferPool {
	private RandomAccessFile file;
	private MyQueue lruCache;
	private boolean cacheFlag;
	private static final int BLOCK_SIZE = 4096;
	private static final int RECORD_SIZE = 4;
	private long endTime;
	/**
	 * 
	 */
	public int cacheHits = 0;
	/**
	 * 
	 */
	public int reads = 0;
	/**
	 * 
	 */
	public int writes = 0;
	
	public BufferPool(RandomAccessFile f, int numBuffers) throws IOException {
		long startTime = System.currentTimeMillis();
		this.file = f;
		int fileSize = (int) file.length();
		this.lruCache = new MyQueue(numBuffers);
		for (int i = 0; i < numBuffers; i++) {
			this.lruCache.add(new myBlock(null, -1));
		}
		
		this.endTime = System.currentTimeMillis() - startTime;
	}
	
	public void remove() throws IOException {
		myBlock block = this.lruCache.remove();
		if (block != null && block.dirtyBit()) {
			this.file.seek(block.index() * BLOCK_SIZE);
			byte[] temp = block.pool();
			this.file.write(temp);
			this.writes++;
			this.file.seek(0);
		}
	}
}
