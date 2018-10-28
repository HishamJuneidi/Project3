import student.TestCase;
import org.junit.Assert;

public class BufferPoolTest extends TestCase {
	private BufferPool bp1;
	private BufferPool bp2;
	private BufferPool bp10;
	
	public void setUp() {
		//bp1 = new BufferPool(1);
		//bp2 = new BufferPool(2);
		//bp10 = new BufferPool(10);
	}
	
	public void testSort() {
		short key = 10;
		short value = 1;
		Record r1 = new Record(key, value);
		key--;
		value++;
		Record r2 = new Record(key, value);
		key--;
		value++;
		Record r3 = new Record(key, value);
		key--;
		value++;
		Record r4 = new Record(key, value);
		key--;
		value++;
		Record r5 = new Record(key, value);
		key--;
		value++;
		Record r6 = new Record(key, value);
		key--;
		value++;
		Record r7 = new Record(key, value);
		key--;
		value++;
		Record r8 = new Record(key, value);
		key--;
		value++;
		Record r9 = new Record(key, value);
		key--;
		value++;
		Record r10 = new Record(key, value);
		/*
		bp1.insert(r1);
		bp2.insert(r2);
		bp2.insert(r1);
		bp10.insert(r8); 
		bp10.insert(r1);
		bp10.insert(r5);
		bp10.insert(r7);
		bp10.insert(r3);
		bp10.insert(r10);
		bp10.insert(r9);
		bp10.insert(r4);
		bp10.insert(r2);
		bp10.insert(r6);
		bp1.sort();
		Record[] a1 = {r1};
		Assert.assertArrayEquals(bp1.pool(), a1);
		bp2.sort();
		Record[] a2 = {r2, r1};
		Assert.assertArrayEquals(bp2.pool(), a2);
		bp10.sort();
		Record[] a10 = {r10, r9, r8, r7, r6, r5, r4, r3, r2, r1};
		Assert.assertArrayEquals(bp10.pool(), a10);
		*/
	}
}
