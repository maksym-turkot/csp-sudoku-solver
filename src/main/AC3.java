package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Implements the AC-3 arc validation algorithm. Used for both preprocessing 
 * and forward-checking variations of the backtracking search.
 * 
 * @author  Max Turkot
 * @version 26/11/2022
 */
public class AC3 {

    /**
     * Runs the AC-3 algorithm. Creates a queue of arc constraints and for 
     * each revises its domain consistency. If revised, updates the queue.
     * 
     * @param constraints to be processed.
     * @param forward true if used for forward checking.
     * @return true if puzzle is consistent so far.
     */
    public boolean ac3(ArrayList<Constraint> constraints, boolean forward) {
        LinkedList<Cell[]> arcs = this.makeArcQueue(constraints);

        // For every arc in the queue.
        while (!arcs.isEmpty()) {
            Cell[] arc = arcs.poll();

            // Check if arc was revised.
            if (this.revise(arc)) {

                // If domain ends up empty, signal inconsistency.
                if (arc[0].getDomainSize() == 0) { 
                    return false;
                }
                
                LinkedList<Cell> neighbors = this.getNeighbors(constraints, arc[0]);
                neighbors.remove(arc[1]);
                
                // Add arcs for neighbors of revised variable.
                for (Cell neighbor : neighbors) {
                    Cell[] newArc = {neighbor, arc[0]};
                    arcs.add(newArc);
                }
            }
        }
        return true;
    }

    /**
     * Generates a queue of arcs for an array of alldif binary 
     * constraints by reducing them down to binary arcs.
     * 
     * @param constraints to reduce to binary arcs.
     * @return lined list of pairs of cells (arcs), binary constraints.
     */
    private LinkedList<Cell[]> makeArcQueue(ArrayList<Constraint> constraints) {
        LinkedList<Cell[]> arcs = new LinkedList<Cell[]>();

        // For every constraint in the list.
        for (Constraint constraint : constraints) {
            int n = constraint.getCellsSize();

            // For every cell arc in the constraint.
            for (int i = 0; i < n - 1; i++) {
                // Loop through cells in the constraint.
                for (int j = i + 1; j < n; j++) {
                    Cell[] arc1 = new Cell[2];
                    arc1[0] = constraint.getCellAt(i);
                    arc1[1] = constraint.getCellAt(j);
                    arcs.add(arc1);

                    Cell[] arc2 = new Cell[2];

                    arc2[0] = constraint.getCellAt(j);
                    arc2[1] = constraint.getCellAt(i);
                    arcs.add(arc2);
                }
            }
        }
        return arcs;
    }

    /**
     * Check if domains of two cells are consistent. If first cell has
     * no value and other does, its value is removed from the first cell.
     * 
     * @param arc to be analyze.
     * @return true if arc was revised.
     */
    public boolean revise(Cell[] arc) {
        boolean revised = false;

        Set<Integer> found = new HashSet<>();

        // For every integer in the domain of first cell.
        for (Integer val1 : arc[0].getDomain()) {
            // For every integer in the second cell.
            for (Integer val2 : arc[1].getDomain()) {
                // Check if domain values match and second cell has a value.
                if (val1 == val2 && arc[1].getDomain().size() == 1) {
                    found.add(val1);
                    revised = true;
                }
            }
        }
        arc[0].getDomain().removeAll(found);

        return revised;
    }

    /**
     * Generates a linked list of all cells in the same constraint as a given 
     * cell.
     * 
     * @param constraints to be processed.
     * @param cell to find neighbors for.
     * @return a list of neighbors for a cell.
     */
    public LinkedList<Cell> getNeighbors(ArrayList<Constraint> constraints, Cell cell) {
        LinkedList<Cell> neighbors = new LinkedList<Cell>();

        // For every constraint in the list.
        for (Constraint constraint : constraints) {
            // Skip constraints that don't contain the cell.
            if (!constraint.cellsContains(cell)) {
                continue;
            }

            // For every cell in the constraint.
            for (int i = 0; i < constraint.getCellsSize(); i++) {
                Cell curr = constraint.getCellAt(i);

                // Skip the cell itself.
                if (curr.equals(cell)) {
                    continue;
                }

                neighbors.add(curr);
            }
        }
        return neighbors;
    }
}
