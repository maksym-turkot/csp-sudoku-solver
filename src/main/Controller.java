package main;

import java.io.File;
import java.util.ArrayList;

/**
 * Contains the main() function that drives the execution of the program.
 * It reads the file with puzzles, solves them using three approaches, and 
 * writes the solutions and execution results to a file.
 * 
 * @author  Max Turkot
 * @version 26/11/2022
 */
public class Controller {
    private static String type;
    private static String id;
    private static double time = 0;
    static int expanded = 0;
    static int depth = 0;
    

    /**
     * Runs the program execution routine. Reads each file in the puzzles 
     * directory, initializes metric tracking variables,
     * and runs the solving mechanism with three variations: plain BFS,
     * AC-3 preprocessing, and forward checking.
     * 
     * @param args standard String array of arguments.
     * @throws Exception general exception thrown by the program.
     */
    public static void main(String[] args) throws Exception {
        Reader rd = new Reader();
        Writer wr = new Writer();
        Solver solver = new Solver();
        File puzzles = new File("data/puzzles");

        // Tracking plain BFS.
        int expClassPlainSum = 0;
        int depthClassPlainSum = 0;
        long timeClassPlainSum = 0;

        // Tracking preprocessing.
        int depthClassAC3Sum = 0;
        int expClassAC3Sum = 0;
        long timeClassAC3Sum = 0;

        // Tracking forward checking.
        int depthClassForSum = 0;
        int expClassForSum = 0;
        long timeClassForSum = 0;

        long startTime;
        long stopTime;

        ArrayList<Cell> cells = new ArrayList<Cell>();
        
        // Read every file in the puzzles directory.
        for (File puzzle : puzzles.listFiles()) {

            // Exclude automatically generated .DS_Store file.
            if (!puzzle.getPath().contains(".DS_Store")) {
                cells = rd.readFile(puzzle.getPath());
                parseFileName(puzzle.getName());
                CSP csp = new CSP(type, id, cells);

                startTime = System.nanoTime();
                solver.solve(csp, false, false);
                stopTime  = System.nanoTime();

                time = (stopTime - startTime) / 1000000.0;

                wr.writeSolution(type, id, cells);

                // Move exe data for classic puzzle to a variable.
                if (type.equals("classic")) {
                    expClassPlainSum += expanded;
                    depthClassPlainSum += depth;
                    timeClassPlainSum += time;
                }
                
                expanded = 0;
                depth = 0;

                cells = rd.readFile(puzzle.getPath());
                parseFileName(puzzle.getName());
                csp = new CSP(type, id, cells);

                startTime = System.nanoTime();
                solver.solve(csp, true, false);
                stopTime  = System.nanoTime();

                time = (stopTime - startTime) / 1000000.0;

                // Move exe data for classic puzzle to a variable.
                if (type.equals("classic")) {
                    expClassAC3Sum += expanded;
                    depthClassAC3Sum += depth;
                    timeClassAC3Sum += time;
                }

                expanded = 0;
                depth = 0;

                cells = rd.readFile(puzzle.getPath());
                parseFileName(puzzle.getName());
                csp = new CSP(type, id, cells);

                startTime = System.nanoTime();
                solver.solve(csp, false, true);
                stopTime  = System.nanoTime();

                time = (stopTime - startTime) / 1000000.0;

                // Move exe data for classic puzzle to a variable.
                if (type.equals("classic")) {
                    expClassForSum += expanded;
                    depthClassForSum += depth;
                    timeClassForSum += time;
                }

                expanded = 0;
                depth = 0;
            }
        }

        wr.writeLog("classic", expClassPlainSum / 10.0, depthClassPlainSum / 10.0, timeClassPlainSum / 10.0,
                expClassAC3Sum / 10.0, depthClassAC3Sum / 10.0, timeClassAC3Sum / 10.0, 
                expClassForSum / 10.0, depthClassForSum / 10.0, timeClassForSum / 10.0);
        
    }

    /**
     * Parses the file name to retrieve the type of the puzzle and the id.
     * @param name of the puzzle file.
     */
    private static void parseFileName(String name) {
        String[] split = name.split("_");
        type = split[0];
        String idStr = split[1];
        id = idStr.substring(0, idStr.length() - 4);
    }
}
