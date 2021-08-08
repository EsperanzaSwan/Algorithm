/*********************************************************************************************
 * Name: Theresa M
 * Date: Aug 6, 2021
 * PercolationStats runs Monte Carlo simulation to estimate the perocalation threshold
 * on a n * n 2D system. It takes two commend-line argument: n and trials. Trials is number
 * of simulation performed. During each trial, it creates a Percolation object with all
 * sites closed, then randomly open sites till the system percolates. The probability stores
 * percolation threshold value. Then we obtain the mean, stddev and 95% confidence interval using
 * methods from StdStats
 *********************************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] probability; // store percolation probability

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        probability = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation iteration = new Percolation(n);
            // iterate till the site percolates
            while (!iteration.percolates()) {
                // Choose a site uniformly at random, and open the site
                // if the site is closed. indices from [1, n]
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!iteration.isOpen(row, col)) {
                    iteration.open(row, col);
                }
            }
            int numOfOpen = iteration.numberOfOpenSites();
            probability[i] = (double) numOfOpen / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(probability);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double stddev;
        // no standard deviation if we only did one trial
        if (probability.length == 1)
            stddev = 0;
        else
            stddev = StdStats.stddev(probability);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (1.96 * stddev()) / Math.sqrt(probability.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.96 * stddev()) / Math.sqrt(probability.length));
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats simulation = new PercolationStats(n, trials);
        double sampleMean = simulation.mean();
        double sampleDev = simulation.stddev();
        double low = simulation.confidenceLo();
        double high = simulation.confidenceHi();
        // format output strings so they have fixed length
        String confOutput = "95% confidence interval";
        int strLen = confOutput.length();
        String meanOutput = String.format("%-" + strLen + "s", "mean");
        String devOutput = String.format("%-" + strLen + "s", "stddev");
        // print out the results
        StdOut.println(meanOutput + " = " + sampleMean);
        StdOut.println(devOutput + " = " + sampleDev);
        StdOut.println(confOutput + " = [" + low + "," + high + "]");
    }

}
