import java.io.File;

import student.TestCase;

/**
 * @author CS3114 staff
 * @version 1
 */

public class VirtualSortTest
    extends TestCase { 
    private CheckFile fileChecker;

    /**
     * This method sets up the tests that follow.
     */
    public void setUp() {
        fileChecker = new CheckFile();
    }

    // ----------------------------------------------------------
    /**
     * Test intialization
     */
    public void testInit() {
        VirtualSort mysort = new VirtualSort();
        assertNotNull(mysort);
       // VirtualSort.main(null);
        //assertFuzzyEquals("Hello, World", systemOut().getHistory());
    }
    
    /**
     * This method tests the main functionality of Quicksort on an "ascii" file
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testQuicksortAscii()
        throws Exception
    {
        String[] args = new String[3];
        args[0] = "input.txt"; //name of file
        args[1] = "10"; //number of buffers
        args[2] = "statFileA.txt";
        //each block 4096 bytes in the file
        VirtualSort.generateFile("input.txt", "1000", 'a'); 
        VirtualSort.generateFile("input2.txt", "10", 'b');
        VirtualSort.generateFile("input3.txt", "10", 'a');
        VirtualSort.main(new String[] {"", "1", "stats.txt"});
        VirtualSort.main(new String[] {"input3.txt", "1"});
        VirtualSort.main(args);
        assertFalse(fileChecker.checkFile("input2.txt"));
        args[0] = "input2.txt";
        args[2] = "statFileB.txt";
        VirtualSort.main(args);
        VirtualSort.main(null);
        assertTrue(fileChecker.checkFile("input.txt"));
        assertTrue(fileChecker.checkFile("input2.txt"));
        VirtualSort.main(new String[] {"input.txt"});
        File f = new File("statFileB.txt");
        File f2 = new File("input.txt");
        File f3 = new File("input2.txt");
        File f4 = new File("input3.txt");
        f.delete();
        f2.delete();
        f3.delete();
        f4.delete();
    }

}
