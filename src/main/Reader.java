package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implements the reader for the program. Opens a file, and parses it 
 * depending on its type indicated in the file name. 
 * 
 * @author  Max Turkot
 * @version 20/11/2022
 */
public class Reader {

    /**
     * Reads the file with a given file path.
     * 
     * @param filepath of a file.
     * @return list of cells.
     */
    public ArrayList<Cell> readFile(String filepath) {
        ArrayList<Cell> cells;

        // Reader exception handling.
        try {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);

        // Check puzzle type in the file name.
        if (filepath.contains("classic")) {
            cells = parseClassic(sc);
        } else if (filepath.contains("triple")) {
            cells = null;
        } else if (filepath.contains("killer")) {
            cells = null;
        } else {
            System.out.println("Unknown puzzle type of \"" + filepath + "\"");
            cells = null;
        }
        sc.close();
        return cells;

        } catch(FileNotFoundException fnfe) {
            System.out.println("Did not find file \"" + filepath + "\"");
            fnfe.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a file with a classic sudoku puzzle.
     * 
     * @param sc scanner of the program.
     * @return list of cells.
     */
    private ArrayList<Cell>  parseClassic(Scanner sc) {
        ArrayList<Cell> cells = new ArrayList<Cell>();

        // Every cell in the file.
        for (int i = 0; i < 81; i++) {
            int row = i / 9;
            int col = i % 9;
            Cell cell = new Cell(sc.nextInt(), row, col);
            cells.add(cell);
        }
        return cells;
    }
}
