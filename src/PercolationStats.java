import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private int trials;
    private double[] partOpen;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        int row, col, rand;
        this.trials = trials;
        this.partOpen = new double[trials];
        Percolation p;
        while (trials-- != 0) {
            p = new Percolation(n);
            while (!p.percolates()) {
                rand = StdRandom.uniform(1, n * n);
                col = rand % n != 0 ? rand % n : n;
                row = rand % n != 0 ? rand / n + 1 : rand / n;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    if (p.percolates())
                        break;
                }
                partOpen[this.trials - trials - 1] = p.numberOfOpenSites() / (double) (n * n);
            }
        }
    }

    public double mean() {
        return StdStats.mean(partOpen);
    }

    public double stddev() {
        return StdStats.stddev(partOpen);
    }

    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.format("95%% confidence interval = [%f, %f]\n", p.confidenceLo(), p.confidenceHi());
    }
}
