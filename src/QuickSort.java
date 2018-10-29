import java.io.IOException;

/**
 * class that does the file sorting
 * @author risha97, hishamj6
 * @version 10/29/2018
 */
public class QuickSort {
    
    private BufferPool bp;
    private static final int RECORD_SIZE = 4;
    
    /**
     * constructor for quicksort class
     * @param pool buffer pool used for sorting
     * @param fileSize size of file
     * @throws IOException
     */
    public QuickSort(BufferPool pool, int fileSize) throws IOException {
        bp = pool;
        this.quicksort(0, (fileSize / RECORD_SIZE) - 1);
    }
    
    /**
     * quicksort algortihm being implemented
     * @param low start index of subarray
     * @param high end index of subarray
     * @throws IOException
     */
    private void quicksort(int low, int high) throws IOException {
        if (low < high) {
            int i;
            int j;
            if (high - low <= 10) {
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
    
    /**
     * swaps 2 records on file
     * @param index1 index of first record being swapped
     * @param index2 index of second record being swapped
     * @throws IOException
     */
    private void swap(int index1, int index2) throws IOException {
        byte[] temp = bp.getBytes(index1);
        bp.insert(bp.getBytes(index2), RECORD_SIZE, index1);
        bp.insert(temp, RECORD_SIZE, index2);
    }
}
