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
	
	private void quicksort(int index1, int index2) throws IOException {
		if (index1 < index2) {
	        int temp1 = index1;
	        int temp2 = index2;
	        short val = bp.key(index1);
	        int count = index1;
	        while (count <= temp2) {
	            if (bp.key(count) < val) {
	                swap(temp1++, count++);
	            }
	            else if (bp.key(count) > val) {
	                swap(count, temp2--);
	            }
	            else {
	                count++;
	            }
	        }
	        quicksort(index1, temp1 - 1);
	        quicksort(temp2 + 1, index2);
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
