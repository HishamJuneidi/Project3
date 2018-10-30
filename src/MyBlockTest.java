import student.TestCase;

/**
 * tests the MyBlock class
 * @author risha97, hishamj6
 * @version 10/30/2018
 */
public class MyBlockTest extends TestCase {
    
    private MyBlock mb;
    private MyBlock mb2;
    
    /**
     * sets up MyBlock for testing
     */
    public void setUp() {
        byte[] bytes = new byte[4];
        mb = new MyBlock(bytes, 5);
        mb2 = new MyBlock(null, 6);
    }
    
    /**
     * tests the index() method
     */
    public void testIndex() {
        assertEquals(5, mb.index());
        assertEquals(6, mb2.index());
    }
}
