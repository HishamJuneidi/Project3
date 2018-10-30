import student.TestCase;

/**
 * tests the MyQueue class
 * @author risha97, hishamj6
 * @version 10/30/2018
 */
public class MyQueueTest extends TestCase {
    
    private MyQueue q;
    
    /**
     * sets up queue class for testing
     */
    public void setUp() {
        q = new MyQueue(20);
    }
    
    /**
     * tests the remove() method
     */
    public void testRemove() {
        assertNull(q.remove());
    }
    
    /**
     * tests the capacity() method
     */
    public void testCapacity() {
        assertEquals(20, q.capacity());
    }
}
