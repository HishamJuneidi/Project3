
public class QuikSort {
	
	private BufferPool bp;
	public QuikSort( ) {
		p
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
}
