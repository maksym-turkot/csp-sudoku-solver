package main;

import java.util.ArrayList;

/**
 * Constructs a data structure for a CSP problem, specifically, sudoku puzzle. 
 * It holds a list of cells (variables) and a list of constraints for the 
 * puzzle. Most methods are getters and setters for data manipulation, as well 
 * as methods for constraint construction.
 * 
 * @author  Max Turkot
 * @version 20/11/2022
 */
public class CSP {
    private String type;
    private String id;
    private ArrayList<Cell> cells;
    private ArrayList<Constraint> constraints;

    /**
     * Constructor initializing variabels.
     * 
     * @param type of the puzzle.
     * @param id of the puzzle.
     * @param cells in the puzzle.
     */
    public CSP(String type, String id, ArrayList<Cell> cells) {
        this.type = type;
        this.id = id;
        this.cells = cells;
        this.constraints = this.createConstraints();
    }

    /**
     * Gets the type of the puzzle.
     * 
     * @return type of the puzzle.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the id of the puzzle.
     * 
     * @return id of the puzzle.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the cells (variables) of the puzzle.
     * 
     * @return cells of the puzzle.
     */
    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    /**
     * Gets a constriant at i-th position.
     * @param i position of a constraint.
     * @return constrant at i-th position.
     */
    public Constraint getConstraintAt(int i) {
        return this.constraints.get(i);
    }
    /**
     * Gets the constraints of the puzzle.
     * 
     * @return constraints of the puzzle.
     */
    public ArrayList<Constraint> getConstraints() {
        return this.constraints;
    }

    /**
     * Creates constraints of the puzzle from the cells. Constraints are
     * ordered as: rows first, cols second, squares last.
     * 
     * @return lilst of constraints.
     */
    private ArrayList<Constraint> createConstraints() {
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();

        this.addRowConstraints(constraints);

        this.addColConstraints(constraints);

        this.addSqrConstraints(constraints);

        return constraints;
    }

    /**
     * Adds row constraints to the CSP.
     * 
     * @param constraints for each row of the puzzle.
     */
    private void addRowConstraints(ArrayList<Constraint> constraints) {

        // Iterate through rows.
        for (int row = 0; row < 81; row += 9) {
            Constraint constraint = new Constraint("alldif");

            // Add all cells in the row.
            for (int col = 0; col < 9; col++) {
                int index = row + col;
                constraint.cellsAdd(this.cells.get(index));
            }
            constraints.add(constraint);
        }
    }

    /**
     * Adds col constraints to the CSP.
     * 
     * @param constraints for each col of the puzzle.
     */
    private void addColConstraints(ArrayList<Constraint> constraints) {

        // Iterate through columns.
        for (int row = 0; row < 9; row++) {
            Constraint constraint = new Constraint("alldif");

            // Add all cells in the col.
            for (int col = 0; col < 81; col += 9) {
                int index = row + col;
                constraint.cellsAdd(this.cells.get(index));
            }
            constraints.add(constraint);
        }
    }

    /**
     * Adds square constraints to the CSP.
     * 
     * @param constraints for each square of the puzzle.
     */
    private void addSqrConstraints(ArrayList<Constraint> constraints) {

        // Iterate through square rows.
        for (int sqrR = 0; sqrR < 81; sqrR += 27) {
            // Iterate through square cols.
            for (int sqrC = 0; sqrC < 9; sqrC += 3) {
                Constraint constraint = new Constraint("alldif");

                // Iterate through each row in the seuqre.
                for (int r = 0; r < 27; r += 9) {
                    // Add all cells in the row of a square.
                    for (int c = 0; c < 3; c++) {
                        int index = sqrR + sqrC + r + c;
                        constraint.cellsAdd(this.cells.get(index));
                    }
                }  
                constraints.add(constraint);
            }
        }
    }
}
