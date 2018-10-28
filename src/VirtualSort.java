import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * { your description of the project here }
 */

//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.


/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author { your name here }
 * @version { put something here }
 */
public class VirtualSort {
    
    /**
     * This method is used to generate a file of a certain size, containing a
     * specified number of records.
     *
     * @param filename the name of the file to create/write to
     * @param blockSize the size of the file to generate
     * @param format the format of file to create
     * @throws IOException throw if the file is not open and proper
     */
    public static void generateFile(String filename, String blockSize,
        char format) throws IOException {
        FileGenerator generator = new FileGenerator();
        String[] inputs = new String[3];
        inputs[0] = "-" + format;
        inputs[1] = filename;
        inputs[2] = blockSize;
        generator.generateFile(inputs);
    }

	/** 
	 * The entry point of the application
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args == null || args.length < 2) {
			System.out.println("Not enough arguments!");
		}
		else {
			int numBlocks = Integer.parseInt(args[1]);
			RandomAccessFile fileToSort = null;
			try {
				fileToSort = new RandomAccessFile(args[0], "rw");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if (fileToSort != null) {
				
				BufferPool bp = new BufferPool(fileToSort, numBlocks);
				bp.closeFile();
				if (args.length == 3) {
					File file = new File(args[2]);
					FileWriter stats;
					if (file.exists()) {
						stats = new FileWriter(file, true);
					}
					else {
						file.createNewFile();
						stats = new FileWriter(file);
					}
					stats.write("File Sorted: " + args[0] + "\n");
					stats.write("Cache Hits: " + bp.hits() + "\n");
					stats.write("File reads: " + bp.reads() + "\n");
					stats.write("File writes: " + bp.writes() + "\n");
					stats.write("Time taken: " + bp.time() + "\n");
					stats.flush();
					stats.close();
				}
			}
			else {
				System.out.println("File with name " + args[0] + " has not been created yet");
			}
		}
		
	}
}
