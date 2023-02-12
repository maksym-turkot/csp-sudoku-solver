package main;

import java.util.HashSet;
import java.util.Set;

/**
 * Constructs the Cell datatype that represents the variable of the CSP. 
 * It has a value field, as well as domain set of possible values. Methods 
 * are simple getters an dsetters that allow for retrieval and manipulation 
 * of data in the domain.
 * 
 * @author  Max Turkot
 * @version 20/11/2022
 */
public class Cell {
    private int val;
    private Set<Integer> domain;

    /**
     * Constructor initializing variables.
     * 
     * @param val that cell holds, 0 for empty.
     * @param r row of the cell.
     * @param c col of the cell.
     */
    public Cell (int val, int r, int c) {
        this.val = val;
        this.domain = new HashSet<Integer>();

        // Check if cell is empty.
        if (val == 0) {
            // Fill the domain.
            for (int i = 1; i < 10; i++) {
                this.domainAdd(i);
            }
        } else {
            this.domainAdd(val);
        }
    }

    /**
     * Gets the value of the cell.
     * 
     * @return value of the cell.
     */
    public int getVal() {
        return this.val;
    }

    /**
     * Sets the value of the cell.
     * 
     * @param val of the cell.
     */
    public void setVal(int val) {
        this.val = val;
    }

    /**
     * Gets the domain of the cell.
     * 
     * @return domain of the cell.
     */
    public Set<Integer> getDomain() {
        return this.domain;
    }

    /**
     * Gets the size of the domain.
     * 
     * @return size of the domain.
     */
    public int getDomainSize() {
        return this.domain.size();
    }

    /**
     * Adds a value to the domain.
     * 
     * @param val to be added.
     */
    public void domainAdd(int val) {
        this.domain.add(Integer.valueOf(val));
    }

    /**
     * Collapses the doomain to a single value.
     * 
     * @param val only value in the domain.
     */
    public void domainCollapse(int val) {
        this.domain.clear();
        this.domain.add(val);
    }

    /**
     * Sets the domain.
     * 
     * @param domain to set.
     */
    public void setDomain(Set<Integer> domain) {
        this.domain = domain;
    }
}
