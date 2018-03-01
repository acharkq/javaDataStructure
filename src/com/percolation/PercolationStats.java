package com.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private int trials;
    final private double mean;
    final private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        int row, col, rand;
        this.trials = trials;
        double[] partOpen = new double[trials];
        Percolation p;
        while (trials-- != 0) {
            p = new Percolation(n);
            while (!p.percolates()) {
                rand = StdRandom.uniform(1, n * n + 1);
                col = rand % n != 0 ? rand % n : n;
                row = rand % n != 0 ? rand / n + 1 : rand / n;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            partOpen[this.trials - trials - 1] = p.numberOfOpenSites() / (double) (n * n);
        }
        mean = StdStats.mean(partOpen);
        stddev = StdStats.stddev(partOpen);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return (mean - 1.96 * stddev / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return (mean + 1.96 * stddev / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.format("95%% confidence interval = [%f, %f]\n", p.confidenceLo(), p.confidenceHi());
    }
}
