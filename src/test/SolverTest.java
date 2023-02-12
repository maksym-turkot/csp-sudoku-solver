package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import main.AC3;
import main.CSP;
import main.Cell;
import main.Reader;
import main.Solver;

/**
 * Test for Solver class.
 * 
 * @author  Max Turkot
 * @version 26/11/2022
 */
public class SolverTest {
    Reader reader;
    ArrayList<Cell> cells;
    CSP csp;
    Solver solver;
    AC3 ac3;

    /**
     * Constructor initializing variables.
     */
    public SolverTest() {
        reader = new Reader();
        solver = new Solver();
        ac3 = new AC3();
    }

    /**
     * Tests backtrackSearch function. Creates a csp, and runs search.
     * Checks if some values were assigned correctly to cells.
     */
    @Test
    public void backtrackSearchTest() {
        cells = reader.readFile("data/puzzles/classic_1.txt");
        csp = new CSP("classic", "1", cells);

        solver.solve(csp, false, false);

        assertEquals(7, cells.get(0).getVal());
        assertEquals(3, cells.get(1).getVal());
        assertEquals(8, cells.get(2).getVal());
        assertEquals(4, cells.get(3).getVal());
        assertEquals(1, cells.get(4).getVal());
        assertEquals(5, cells.get(5).getVal());
        assertEquals(2, cells.get(6).getVal());
        assertEquals(6, cells.get(7).getVal());
        assertEquals(9, cells.get(8).getVal());
        assertEquals(6, cells.get(9).getVal());
        assertEquals(5, cells.get(10).getVal());
        assertEquals(1, cells.get(11).getVal());
        assertEquals(9, cells.get(12).getVal());
        assertEquals(2, cells.get(13).getVal());
        assertEquals(8, cells.get(14).getVal());
        assertEquals(4, cells.get(15).getVal());
        assertEquals(7, cells.get(16).getVal());
        assertEquals(3, cells.get(17).getVal());
        assertEquals(8, cells.get(27).getVal());
        assertEquals(7, cells.get(38).getVal());
        assertEquals(1, cells.get(45).getVal());
        assertEquals(1, cells.get(55).getVal());
        assertEquals(7, cells.get(77).getVal());
        assertEquals(4, cells.get(80).getVal());
    }

    /**
     * Tests revise function. Checks if pairs of cells are compaired and if 
     * program indicates revision.
     */
    @Test
    public void reviseTest() {
        Cell cell0 = new Cell(0, 0, 0);
        Cell cell1 = new Cell(1, 0, 0);

        Cell[] pair1 = {cell0, cell1};
        Cell[] pair2 = {cell1, cell0};

        assertTrue(ac3.revise(pair1));
        assertFalse(ac3.revise(pair2));
    }

    /**
     * Tests getneighbors function. Checks if for a given cell, 
     * list contains correct cells with values.
     */
    @Test
    public void getNeighborsTest() {
        cells = reader.readFile("data/solutions/classic_1-sol.txt");
        csp = new CSP("classic", "1", cells);

        LinkedList<Cell> neighbors = ac3.getNeighbors(csp.getConstraints(), csp.getCells().get(0));
        
        assertEquals(3, neighbors.get(0).getVal());
        assertEquals(8, neighbors.get(1).getVal());
        assertEquals(1, neighbors.get(12).getVal());
        assertEquals(3, neighbors.get(16).getVal());
        assertEquals(5, neighbors.get(19).getVal());
        assertEquals(9, neighbors.get(23).getVal());
    }
}
