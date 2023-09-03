/* *****************************************************************************
 *  Name:              Ricardo Paschoeto
 *  Coursera User ID:  123456
 *  Last modified:     July 24, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] openedSites;
    private double term;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        term = 1.96 / Math.sqrt(trials);

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("It is outside its prescribed range");
        }
        openedSites = new double[trials];
        double opened;
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);
            do {
                int randCol = StdRandom.uniformInt(1, n + 1);
                int randRow = StdRandom.uniformInt(1, n + 1);
                percolation.open(randRow, randCol);
            } while (!percolation.percolates());

            opened = percolation.numberOfOpenSites();
            openedSites[t] = opened / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {

        return StdStats.mean(openedSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return StdStats.stddev(openedSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - term * stddev();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + term * stddev();
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.print("mean                    = ");
        StdOut.println(stats.mean());
        StdOut.print("stddev                  = ");
        StdOut.println(stats.stddev());
        StdOut.print("95% confidence interval = ");
        StdOut.print("[");
        StdOut.print(stats.confidenceLo());
        StdOut.print(", ");
        StdOut.print(stats.confidenceHi());
        StdOut.println("]");

    }
}
