package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Cell;
import main.Reader;

/**
 * Test for Reader class.
 * 
 * @author  Max Turkot
 * @version 20/11/2022
 */
public class ReaderTest {
    Reader reader;
    ArrayList<Cell> cells;

    /**
     * Constructor initializing variables.
     */
    public ReaderTest() {
        reader = new Reader();
    }

    /**
     * Tests readFile funciton. Reads from a file, and checks if values were 
     * saved correctly.
     */
    @Test
    public void readFileTest() {
        cells = reader.readFile("data/puzzles/classic_1.txt");

        assertEquals(7, cells.get(0).getVal());
        assertEquals(0, cells.get(3).getVal());
        assertEquals(7, cells.get(77).getVal());
    }
}
