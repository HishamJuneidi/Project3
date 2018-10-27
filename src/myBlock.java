import java.nio.ByteBuffer;

public class myBlock {
	private byte[] pool;
	private boolean dirtyBit;
	private int index;
	
	public myBlock(byte[] p, int i) {
		if (p != null) {
			this.pool = p;
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
