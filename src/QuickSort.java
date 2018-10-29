import java.io.IOException;

public class QuickSort {
	
	/**
	 * 
	 */
	public BufferPool bp;
	private static final int RECORD_SIZE = 4;
	
	public QuickSort(BufferPool pool, int fileSize) throws IOException {
		bp = pool;
		this.quicksort(0, (fileSize/ RECORD_SIZE) - 1);
	}
	
	private void quicksort(int low, int high) throws IOException {
		if (low < high) {
			int i;
			int j;
			if (high - low <= 50) {
				for (i = low + 1; i <= high; i++) {
					for (j = i; (j > low) && bp.key(j - 1) > bp.key(j); j--) {
						swap(j, j - 1);
					}
				}
			}
			else {
		        int index1 = low;
		        int index2 = high;
		        short val = bp.key(low);
		        int count = low;
		        while (count <= index2) {
		            if (bp.key(count) < val) {
		                swap(index1++, count++);
		            }
		            else if (bp.key(count) > val) {
		                swap(count, index2--);
		            }
		            else {
		                count++;
		            }
		        }
		        quicksort(low, index1 - 1);
		        quicksort(index2 + 1, high);
			}
		}
	}
	
	
	
	
	private void swap(int index1, int index2) throws IOException {
		byte[] temp = bp.getBytes(index1);
		bp.insert(bp.getBytes(index2), RECORD_SIZE, index1);
		bp.insert(temp, RECORD_SIZE, index2);
	}
}
