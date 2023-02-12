package main;

import java.util.ArrayList;

/**
 * Constructs the constraint data type for the Sudoku puzzle. Constraints can 
 * be of two types: “alldiff” for most sudoku constraints, and “sum” for 
 * killer sudoku. Constraint contains a list of cells that participate in this 
 * constraint. Methods consist of getters and setters for data manipulation.
 * 
 * @author  Max Turkot
 * @version 20/11/2022
 */
public class Constraint {
    private String type;
    private ArrayList<Cell> cells;

    /**
     * Constructor initializing varuables.
     * 
     * @param type of the constraint.
     */
    public Constraint(String type) {
        this.type = type;
        this.cells = new ArrayList<Cell>();
    }

    /**
     * Gets the type of the constraint.
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Adds a cell to the constraint.
     * 
     * @param cell to add.
     */
    public void cellsAdd(Cell cell) {
        cells.add(cell);
    }

    /**
     * Gets the cell at the i-th position.
     * 
     * @param i position of the cell.
     * @return cell at i-th position.
     */
    public Cell getCellAt(int i) {
        return this.cells.get(i);
    }

    /**
     * Gets the value of a cell ath the i-th position.
     * 
     * @param i position of the cell.
     * @return value of the cell.
     */
    public int getCellValAt(int i) {
        return this.cells.get(i).getVal();
    }

    /**
     * Gets the number of cells in the constriant.
     * 
     * @return number of cells in the constraint.
     */
    public int getCellsSize() {
        return this.cells.size();
    }

    /**
     * Checks if constriant contains a cell.
     * 
     * @param cell to check.
     * @return true if cell is in the constriant.
     */
    public boolean cellsContains(Cell cell) {
        return this.cells.contains(cell);
    }
}
