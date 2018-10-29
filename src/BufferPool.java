import java.io.IOException;
import java.io.RandomAccessFile;


public class BufferPool {
	private RandomAccessFile file;
	private MyQueue lruCache;
	private boolean cacheFlag;
	private static final int BLOCK_SIZE = 4096;
	private static final int RECORD_SIZE = 4;
	private long endTime;
	private int cacheHits = 0;
	private int reads = 0;
	private int writes = 0;
	
	public BufferPool(RandomAccessFile f, int numBuffers) throws IOException {
		this.file = f;
		this.lruCache = new MyQueue(numBuffers);
		int i;
		for (i = 0; i < numBuffers; i++) {
			this.lruCache.add(new myBlock(null, -1));
		}
		
	}
	
	public myBlock remove() throws IOException {
		myBlock block = this.lruCache.remove();
		if (block != null && block.dirtyBit()) {
			this.file.seek(block.index() * BLOCK_SIZE);
			byte[] temp = block.pool();
			this.file.write(temp);
			this.writes++;
			this.file.seek(0);
		}
		return block;
	}
	
	public myBlock getBlock(int index) throws IOException {
		int offset = (index * RECORD_SIZE) / BLOCK_SIZE;
		myBlock output = this.lruCache.search(offset);
		if (output == null) {
			if (lruCache.size() == lruCache.capacity()) {
				output = this.remove();
				output.setIndex(offset);
				output.setDirtyBit(false);
			}
			else {
				output = new myBlock(null, offset);
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
	
	public short key(int index) throws IOException {
		short output = 0;
		if (this.cacheFlag) {
			this.cacheHits += 1;
		}
		myBlock b = this.getBlock(index);
		int offset = (index * RECORD_SIZE) % BLOCK_SIZE;
		output = b.key(offset);
		return output;
	}
	
	public void insert(byte[] bytes, int size, int index) throws IOException {
		myBlock output = this.getBlock(index);
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
	
	public byte[] getBytes(int index) throws IOException {
		myBlock output = this.getBlock(index);
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
	
	public int hits() {
		return this.cacheHits;
	}
	
	public int reads() {
		return this.reads;
	}
	
	public int writes() {
		return this.writes;
	}
	
	public MyQueue cache() {
		return this.lruCache;
	}
	
	public long time() {
		return (this.endTime / 1000000);
	}
	
	public void closeFile() throws IOException {
		this.file.close();
	}
}
