import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;


public class BufferPool {
	private File file;
	
	//read(path);
	private int size;
	private int insertion;
	private Record[] pool;
	
	public BufferPool(int s) {
		this.size = s;
		insertion = 0;
		pool = new Record[this.size];
	}
	
	public Record[] pool() {
		return this.pool;
	}
	
	public void setFile(String filename) {
		this.file = new File(filename);
	}
	
	public void insert(Record r) {
		if (insertion == size) {
			for (int i = size - 2; i <= 0; i--) {
				this.pool[i + 1] = this.pool[i];
			}
			this.pool[0] = r;
			insertion = 1;
		}
		else {
			this.pool[insertion] = r;
			insertion++;
		}
	}
	
	public void write(int numBuffers, int offset) throws IOException {
		RandomAccessFile f = new RandomAccessFile(this.file, "rw");
		int len = numBuffers * 4096;
		int startingIndex = offset / 4;
		int numRecords = numBuffers * 1024;
		ByteBuffer key = ByteBuffer.allocate(2);
		ByteBuffer val = ByteBuffer.allocate(2);
		byte[] byteArray = new byte[len];
		int j = 0;
		for (int i = startingIndex; i < numRecords; i++) {
			key.putShort(0, this.pool[i].key());
			val.putShort(0, this.pool[i].value());
			byteArray[j] = key.array()[0];
			byteArray[j + 1] = key.array()[1];
			byteArray[j + 2] = val.array()[0];
			byteArray[j + 3] = val.array()[1];
			j += 4;
		}
		f.write(byteArray, offset, len);
		f.close();
	}
	
	public void sort() {
		this.quicksort(0, this.size - 1);
	}
	
	private void quicksort(int low, int high) {
		if (low < high) {
			int partitionIndex = this.partition(low, high);
			
			this.quicksort(low, partitionIndex - 1);
			this.quicksort(partitionIndex + 1, high);
		}
	}
	
	private int partition(int low, int high) {
		short pivot = this.pool[high].key();
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (this.pool[j].key() <= pivot) {
				i++;
				this.swap(i, j);
			}
		}
		this.swap(i + 1, high);
		return i + 1;
	}
	
	private void swap(int index1, int index2) {
		Record temp = this.pool[index1];
		this.pool[index1] = this.pool[index2];
		this.pool[index2] = temp;
	}
	
	public void read(int numBuffers, int offset) throws IOException {
		RandomAccessFile f = new RandomAccessFile(this.file, "r");
		int len = numBuffers * 4096;
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);
		byte[] byteArray = new byte[len];
		f.read(byteArray, offset, len);
		for (int i = 0; i < byteArray.length; i += 4) {
			byteBuffer.put(0, byteArray[i]);
			byteBuffer.put(1, byteArray[i + 1]);
			short key = byteBuffer.getShort(0);
			byteBuffer.put(0, byteArray[i + 2]);
			byteBuffer.put(1, byteArray[i + 3]);
			short val = byteBuffer.getShort(0);
			this.insert(new Record(key, val));
		}
		f.close();
	}
}
