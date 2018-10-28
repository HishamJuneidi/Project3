import java.io.IOException;

public class QuickSort {
	
	/**
	 * 
	 */
	public static BufferPool bp;
	private static final int RECORD_SIZE = 4;
	
	public QuickSort(BufferPool pool, int fileSize) throws IOException {
		bp = pool;
		this.quicksort(0, (fileSize/ RECORD_SIZE) - 1);
	}
	
	private void quicksort(int low, int high) throws IOException {
		if (low < high) {
			int index1 = low;
			int index2 = high;
			short val = bp.key(low);
			int count = low;
			while (count <= index2) {
				if (bp.key(count) < val) {
					this.swap(index1, count);
					index1++;
					count++;
				}
				else if (bp.key(count) > val) {
					this.swap(count, index2);
					index2--;
				}
				else {
					count++;
				}
			}
			this.quicksort(low, index1 - 1);
			this.quicksort(index2 + 1, high);
		}
	}
	
	
	
	private void swap(int index1, int index2) throws IOException {
		byte[] temp1 = new byte[RECORD_SIZE];
		byte[] temp2 = new byte[RECORD_SIZE];
		bp.getBytes(temp1, RECORD_SIZE, index1);
		bp.getBytes(temp2, RECORD_SIZE, index2);
		bp.insert(temp1, RECORD_SIZE, index2);
		bp.insert(temp2, RECORD_SIZE, index1);
	}
}
