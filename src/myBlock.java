import java.nio.ByteBuffer;

public class myBlock {
	private byte[] pool;
	private boolean dirtyBit;
	private int index;
	private static final int BLOCK_SIZE = 4096;
	
	public myBlock(byte[] p, int i) {
		if (p != null) {
			this.pool = p;
		}
		else {
			this.pool = new byte[BLOCK_SIZE];
		}
		this.index = i;
		this.dirtyBit = false;
	}
	
	public short key(int index) {
		short output = 0;
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.put(pool, index, 2);
		output = bb.getShort(0);
		return output;
	}
	
	public byte[] pool() {
		return this.pool;
	}
	
	public void setPool(byte[] p) {
		this.pool = p;
	}
	
	public boolean dirtyBit() {
		return this.dirtyBit;
	}
	
	public void setDirtyBit(boolean b) {
		this.dirtyBit = b;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}
	
	public int index() {
		return this.index;
	}
}
