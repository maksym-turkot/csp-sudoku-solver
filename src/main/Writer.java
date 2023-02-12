package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implements the writer for the program. Writes both solutions and log files.
 * 
 * @author  Max Turkot
 * @version 26/11/2022
 */
public class Writer {

    /**
     * Writes solved puzzle to the file.
     * 
     * @param type of the puzzle.
     * @param id of the puzzle.
     * @param cells of the puzzle.
     */
    public void writeSolution(String type, String id, ArrayList<Cell> cells) {

        // Writer exception handling.
        try {
            File file = this.createFile("data/solutions/" + type + "_" + id + "-sol.txt");
            FileWriter myWriter = new FileWriter(file.getPath());

            // Check if writing a classic puzzle.
            if (type.equals("classic")) {

                // For every cell.
                for (int i = 0; i < 81; i++) {
                    myWriter.write(String.valueOf(cells.get(i).getVal()) + " ");

                    // Insert a space every 3 cells.
                    if ((i + 1) % 3 == 0) {
                        myWriter.write(" ");
                    }

                    // Start a new line every 9 cells.
                    if (i % 9 == 8) {
                        myWriter.write("\n");
                    }

                    // Insert an extra new line every 3 rows.
                    if ((i + 1) % 27 == 0 && i != 0) {
                        myWriter.write("\n");
                    }
                }
            }       

            myWriter.close();

          } catch (IOException ioe) {
            System.out.println("Error writing file \"data/solutions/" + type + "_" + id + ".txt\"");
            ioe.printStackTrace();
          }
    }

    /**
     * Writes a log file.
     * 
     * @param type of the puzzle.
     * @param expandedP avg variables expanded for plain DFS.
     * @param depthP avg tree depth for plain DFS.
     * @param timeP avg exe time for plain DFS.
     * @param expandedAavg variables expanded for AC-3 preprocessing.
     * @param depthA avg tree depth for AC-3 preprocessing.
     * @param timeA avg exe time for AC-3 preprocessing.
     * @param expandedF avg variables expanded for forward checking.
     * @param depthF avg tree depth for forward checking.
     * @param timeF avg exe time for forward checking.
     */
    public void writeLog(String type, double expP, double depP, double timeP,
            double expA, double depA, double timeA, 
            double expF, double depF, double timeF) {
        
        // Writer exception handling.
        try {
            File file = this.createFile("data/log.txt");

            FileWriter myWriter = new FileWriter(file.getPath(), true);

            myWriter.write( "************************" + "\n" +
                            "type:          " + type   + "\n" +
                            "========================" + "\n" +
                            "plain DFS"                + "\n" +
                            "------------------------" + "\n" +
                            "expanded:      " + expP   + "\n" +
                            "depth:         " + depP   + "\n" +
                            "execTime (ms): " + timeP  + "\n" +
                            "========================" + "\n" +
                            "AC-3 preprocessing"       + "\n" +
                            "------------------------" + "\n" +
                            "expanded:      " + expA   + "\n" +
                            "depth:         " + depA   + "\n" +
                            "execTime (ms): " + timeA  + "\n" +
                            "========================" + "\n" +
                            "forward checking"         + "\n" +
                            "------------------------" + "\n" +
                            "expanded:      " + expF   + "\n" +
                            "depth:         " + depF   + "\n" +
                            "execTime (ms): " + timeF  + "\n" +
                            "************************" + "\n" +
                            ""                         + "\n");

            myWriter.close();

            } catch (IOException ioe) {
            System.out.println("Error writing file \"log\"");
            ioe.printStackTrace();
        }
    }

    /**
     * Creates a file for writing.
     * 
     * @param filename of the file.
     * @return handle of the file.
     */
    private File createFile(String filename) {

        // Writer exception handling
        try {
            File file = new File(filename);
            
            file.createNewFile();
            
            return file;
          } catch (IOException ioe) {
            System.out.println("Error opening file \"" + filename + "\"");
            ioe.printStackTrace();

            return null;
        }
    }
}
