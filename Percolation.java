/* *****************************************************************************
 *  Name:              Ricardo Paschoeto
 *  Coursera User ID:  123456
 *  Last modified:     July 24, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private int count = 0;
    private int virtualTop;
    private int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Parameter is zero or negative");

        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n + 1][n + 1];

        virtualTop = n * n + 1;
        virtualBottom = n * n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // Include new boundaries verification
        if (row > grid[0].length - 1 || col > grid[1].length - 1 || row <= 0 || col <= 0)
            throw new IllegalArgumentException("Index Out of Range!");

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            count++;
            neighbors(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > grid[0].length - 1 || col > grid[1].length - 1 || row <= 0 || col <= 0)
            throw new IllegalArgumentException("Index Out of Range!");

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > grid[0].length || col > grid[1].length)
            throw new IllegalArgumentException("Index Out of Range!");

        if (isOpen(row, col)) {
            int p = map2Dto1D(row, col);
            if (uf.find(p) == uf.find(virtualTop))
                return true;
        }

        return false;
    }

    private int map2Dto1D(int row, int col) {
        return (row - 1) * (grid.length - 1) + (col - 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(virtualTop) == uf.find(virtualBottom)) {
            return true;
        }
        return false;
    }

    private void connectTopBottom(int row, int col) {
        if (isOpen(row, col)) {
            if (row == 1) {
                int top = map2Dto1D(row, col);
                uf.union(top, virtualTop);
            }

            if (row == grid.length - 1 || grid.length - 1 == 1) {
                int bottom = map2Dto1D(row, col);
                uf.union(bottom, virtualBottom);
            }
        }
    }

    // Method to set up the neighboors.
    private void neighbors(int row, int col) {
        connectTopBottom(row, col);
        if (row - 1 > 0) {
            testNeighbors(row, col, row - 1, col);
            connectTopBottom(row - 1, col);
        }
        if (col - 1 > 0) {
            testNeighbors(row, col, row, col - 1);
            connectTopBottom(row, col - 1);
        }
        if (col + 1 < grid[1].length) {
            testNeighbors(row, col, row, col + 1);
            connectTopBottom(row, col + 1);
        }
        if (row + 1 < grid[0].length) {
            testNeighbors(row, col, row + 1, col);
            connectTopBottom(row + 1, col);
        }

    }

    private void testNeighbors(int row, int col, int ngr, int ngc) {
        int p = map2Dto1D(row, col);
        if (isOpen(ngr, ngc)) {
            int ng = map2Dto1D(ngr, ngc);
            if (uf.find(p) != uf.find(ng))
                uf.union(ng, p);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // Empty body method.
    }
}
