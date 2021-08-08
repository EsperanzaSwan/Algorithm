/*********************************************************************************************
 * Name: Theresa M
 * Date: Aug 8, 2021
 * Peroclation creates grids of n * n. It uses WeightedQuickUnionUF object from
 * algs4.jar to connect sites and find if two sites are connected. open() connects
 * a site at position (row, col) to adjacent open sites. percolates() determines if
 * the system percolates. isOpen() determines if a site at position (row, col)
 * is open. isFull() determines if a site at position (row, col) is connected to
 * the top. numberOfOpenSites() returns the number of open sites.
 *********************************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] grids; // grids of n * n to map the sites
    private final int size; // size of the grids along one axis. Size is set equal to n
    private int numOfOpenSites; // the number of open sites
    private WeightedQuickUnionUF myUF; // union find object to track the system connectivity
    private WeightedQuickUnionUF backwashPreventer; // a copy of union find object to prevent backwash

    // class constructor
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        // create grids and set all sites to closed (0)
        grids = new int[n * n];
        for (int i = 0; i < n * n; i++)
            grids[i] = 0;
        size = n;
        numOfOpenSites = 0;
        // create union find object and add one top and one bottom virtual site to the end
        // virtual top: n^2 virtual bottom: n^ + 1
        myUF = new WeightedQuickUnionUF((n * n) + 2);
        int virtureTop = n * n;
        int virtureBottom = n * n + 1;
        // all the top sites are connected to the virtual top site
        for (int i = 0; i < size; i++)
            myUF.union(i, virtureTop);
        // all the bottom sites are connected to the virtual bottom site
        for (int i = 0; i < size; i++)
            myUF.union(((size - 1) * size + i), virtureBottom);
        // create a union find object for backwashPreventer
        // excluding virture bottom site
        backwashPreventer = new WeightedQuickUnionUF(n * n + 1);
        // all the top sites are connected to the virtual top site
        for (int i = 0; i < size; i++)
            backwashPreventer.union(i, virtureTop);
    }

    // check if row and col are out of bounds
    private void checkBounds(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();
    }

    // open the site if not open
    public void open(int row, int col) {
        this.checkBounds(row, col);
        // only open a site when it is not open
        if (!this.isOpen(row, col)) {
            int p = (row - 1) * size + col - 1;
            grids[p] = 1;
            numOfOpenSites++;
            // connect to the adjacent open sites
            // check to make sure indices not out of bounds first
            if (row > 1) {
                if (this.isOpen(row - 1, col)) {
                    myUF.union(p, p - size);
                    backwashPreventer.union(p, p - size);
                }

            }
            if (row < size) {
                if (this.isOpen(row + 1, col)) {
                    myUF.union(p, p + size);
                    backwashPreventer.union(p, p + size);
                }
            }
            if (col > 1) {
                if (this.isOpen(row, col - 1)) {
                    myUF.union(p, p - 1);
                    backwashPreventer.union(p, p - 1);
                }
            }
            if (col < size) {
                if (this.isOpen(row, col + 1)) {
                    myUF.union(p, p + 1);
                    backwashPreventer.union(p, p + 1);
                }
            }
        }
    }

    // is the site open?
    public boolean isOpen(int row, int col) {
        this.checkBounds(row, col);
        int p = (row - 1) * size + col - 1;
        return grids[p] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.checkBounds(row, col);
        // first check if the site is open
        if (this.isOpen(row, col)) {
            // if size == 1, the site is full since it is the top site
            if (size == 1)
                return true;
            else {
                // convert to the index for union find
                int p = (row - 1) * size + col - 1;
                return backwashPreventer.find(p) == backwashPreventer.find(size * size);
            }
        } else
            return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // the system percolate when virtual top connects virtureBottom
    public boolean percolates() {
        // if size == 1, only need to know if the site is open
        if (size == 1)
            return grids[size - 1] == 1;
        else {
            int virtureTop = size * size;
            int virtureBottom = size * size + 1;
            return myUF.find(virtureTop) == myUF.find(virtureBottom);
        }
    }
}

