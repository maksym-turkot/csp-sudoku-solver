package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Runs the generic CSP solution algorithm. At the core is the backtracking 
 * algorithm and helper methods for it. A separate forward checking method is 
 * used for the respective solver configuration.
 * 
 * @author  Max Turkot
 * @version 26/11/2022
 */
public class Solver {
    private AC3 ac3 = new AC3();

    /**
     * Solves the generic puzzle. Has two switches, one for AC-3 preprocessing, 
     * and one for forward checking. Calls AC-3 if preprocessing, and 
     * backtrackSearch() afterwards.
     * 
     * @param csp generic puzzle structure.
     * @param pre true if preprocessing requested.
     * @param forward true if forward checking requested.
     * @return list of cells of a solved puzzle.
     */
    public ArrayList<Cell> solve(CSP csp, boolean pre, boolean forward) {
        // If preprocessing requested.
        if (pre) {
            // If forward checking didn't find insonsistency.
            if (ac3.ac3(csp.getConstraints(), false)) {
                // If puzzle is complete.
                if (this.isComplete(csp.getCells()))
                    return csp.getCells();
            } else {
                return null;
            }
        }

        return backtrackSearch(csp, forward);
    }
    
    /**
     * Wrapper function for a recursive depth-first search.
     * 
     * @param csp generic puzzle structure.
     * @param forward true if forward checking requested.
     * @return list of cells of a solved puzzle.
     */
    private ArrayList<Cell> backtrackSearch(CSP csp, boolean forward) {
        return backtrack(csp, csp.getCells(), forward);
    }

    /**
     * Depth-first search algorithm. Selects unassigned variable in the csp, 
     * picks a value, checks for consistency, and backtracks if failure found 
     * down the road. Includes a forward checking switch.
     * 
     * @param csp generic puzzle structure.
     * @param cells current assignemtn of cells of a puzzle.
     * @param forward true if forward checking used.
     * @return list of cells of a spuzzle.
     */
    private ArrayList<Cell> backtrack(CSP csp, ArrayList<Cell> cells, boolean forward) {
        Controller.depth++;

        /**
         * Check if puzzle is complete at this point.
         */
        if (this.isComplete(cells))
            return cells;

        Cell var = this.selectUnassignedVar(cells, false); // TODO

        // For every value in the cell's domain.
        for(Integer val : this.orderDomainVals(csp, var, cells)) { // TODO
            Controller.expanded++;

            // Check if assignemt will be consistent.
            if (this.isConsistent(csp, var, val)) {
                int ogVal = var.getVal();
                Set<Integer> ogDom = new HashSet<Integer>();
                ogDom.addAll(var.getDomain());

                var.setVal(val);
                var.domainCollapse(val);

                // Skip this value if forward check finds an issue.
                if (forward && !this.forwardCheck(csp, var)) {
                    var.setVal(ogVal);
                    var.setDomain(ogDom);
                    continue;
                }

                ArrayList<Cell> result = backtrack(csp, cells, forward);

                // If backtrack was successful.
                if (result != null) {
                    return cells;
                } else {
                    var.setVal(ogVal);
                    var.setDomain(ogDom);
                }
            }
        }
        return null;
    }

    /**
     * Checks if assignment is complete.
     * 
     * @param cells current assignment.
     * @return true if assignmetn is complete.
     */
    private boolean isComplete(ArrayList<Cell> cells) {
        // for each cell in the assignment.
        for (Cell cell : cells) {
            // Check if cell is empty.
            if (cell.getVal() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Selects an unassigned variable.
     * 
     * @param cells current assignment.
     * @param mrv true if MRV is used.
     * @return cell (variable) picked for next assignment.
     */
    private Cell selectUnassignedVar(ArrayList<Cell> cells, boolean mrv) {
        // For each cell in the assignment.
        for (Cell cell : cells) {
            // Check if a cell is empty.
            if (cell.getVal() == 0) {
                return cell;
            }
        }
        System.out.println("No unassigned variables.");
        return null;
    }

    /**
     * Orders values of a domain.
     * 
     * @param csp generic puzzle structure.
     * @param var variable to order domain for.
     * @param cells current assignment.
     * @return sorted domain set for the variable.
     */
    private SortedSet<Integer> orderDomainVals(CSP csp, Cell var, ArrayList<Cell> cells) {
        SortedSet<Integer> ordDom = new TreeSet<Integer>();

        // For each value in the domain.
        for (Integer val : var.getDomain()) {
            ordDom.add(val);
        }
        return ordDom;
    }

    /**
     * Checks if assignment is consistent.
     * 
     * @param csp generic puzzle structure.
     * @param var variable to check for consistency.
     * @param val to check consistency for.
     * @return true if assignment is consistent.
     */
    private boolean isConsistent(CSP csp, Cell var, int val) {

        // For every constraint in the problem.
        for (Constraint constraint : csp.getConstraints()) {
            // Skip constraints that don't contain the cell.
            if (!constraint.cellsContains(var)) {
                continue;
            }

            int n = constraint.getCellsSize();

            // For every cell in the constraint.
            for (int i = 0; i < n; i++) {
                // Check if it already contains the assigned value.
                if (val == constraint.getCellValAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Forward checking algorithm. Creates a copy of domains for each variable 
     * and runs AC-3 on the variable's constraints. Reassignes domains and 
     * returns the result.
     * 
     * @param csp generic puzzle structure.
     * @param var variable to forward check on.
     * @return true if forward check was successful.
     */
    private boolean forwardCheck(CSP csp, Cell var) {
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        HashMap<Cell, Set<Integer>> ogDomMap = new HashMap<Cell, Set<Integer>>();
        boolean result = false;
        
        // For each constraint in the problem.
        for (Constraint constraint : csp.getConstraints()) {
            // Skip constraints that don't contain the cell.
            if (!constraint.cellsContains(var)) {
                continue;
            }
            constraints.add(constraint);
        }

        // Save domains for each cell in the problem.
        for (Cell cell : csp.getCells()) {
            Set<Integer> ogDom = new HashSet<Integer>();
            ogDom.addAll(cell.getDomain());
            ogDomMap.put(cell, ogDom);
        }

        result = ac3.ac3(constraints, true);

        // Restore domains for each cell.
        for (Cell cell : ogDomMap.keySet()) {
            cell.setDomain(ogDomMap.get(cell));
        }
        return result;
    }
}
