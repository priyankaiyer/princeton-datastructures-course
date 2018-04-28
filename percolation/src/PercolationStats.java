import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolationThreshold;
    private final int trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be greater than 0");
        }
        this.trials = trials;
        Percolation percolation;
        percolationThreshold = new double[trials];
        for (int trialNumber = 0; trialNumber < trials; trialNumber++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            percolationThreshold[trialNumber] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(percolationThreshold);
        stddev = StdStats.stddev(percolationThreshold);
    }

    public double mean() {
        return mean;
    }                          // sample mean of percolation threshold

    public double stddev() {
        return stddev;
    }                                           // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean - ((CONFIDENCE_95 * stddev) / Math.sqrt(trials));
    }                                     // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean + ((CONFIDENCE_95 * stddev) / Math.sqrt(trials));
    }                                    // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean " + percolationStats.mean());
        System.out.println("stddev " + percolationStats.stddev());
        System.out.println("95%conf lo " + percolationStats.confidenceLo());
        System.out.println("95%conf hi " + percolationStats.confidenceHi());
    }        // test client (described below)
}
