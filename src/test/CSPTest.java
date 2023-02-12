package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.CSP;
import main.Cell;
import main.Reader;

/**
 * Test for CSP class.
 * 
 * @author  Max Turkot
 * @version 20/11/2022
 */
public class CSPTest {
    Reader reader;
    CSP csp;
    ArrayList<Cell> cells;

    /**
     * Constructor initializing variables.
     */
    public CSPTest() {
        reader = new Reader();
    }

    /**
     * Tests createConstraints function. Creates constraints, and then
     * checks if some constraints contain expected values at expected 
     * locations.
     */
    @Test
    public void createConstraintsTest() {
        cells = reader.readFile("data/puzzles/classic_1.txt");
        csp = new CSP("classic", "1", cells);
        
        // Test Row Constraints
        assertEquals(7, csp.getConstraintAt(0).getCellValAt(0));
        assertEquals(0, csp.getConstraintAt(0).getCellValAt(3));
        assertEquals(0, csp.getConstraintAt(4).getCellValAt(6));
        assertEquals(9, csp.getConstraintAt(7).getCellValAt(4));

        // Test Col Constriants
        assertEquals(0, csp.getConstraintAt(9).getCellValAt(3));
        assertEquals(8, csp.getConstraintAt(12).getCellValAt(4));
        assertEquals(0, csp.getConstraintAt(15).getCellValAt(6));
        assertEquals(0, csp.getConstraintAt(17).getCellValAt(8));

        // Test Square Constraints
        assertEquals(4, csp.getConstraintAt(18).getCellValAt(7));
        assertEquals(0, csp.getConstraintAt(23).getCellValAt(1));
        assertEquals(3, csp.getConstraintAt(24).getCellValAt(6));
        assertEquals(9, csp.getConstraintAt(26).getCellValAt(7));
    }
}
