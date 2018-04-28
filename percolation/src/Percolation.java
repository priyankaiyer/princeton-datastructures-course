import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int virtualSiteTop;
    private final int virtualSiteBottom;
    private final int size;
    private int openSites = 0;
    private boolean[][] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF fullness;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than 0");
        this.size = n;
        virtualSiteTop = (n * n);
        virtualSiteBottom = (n * n) + 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
        fullness = new WeightedQuickUnionUF((n * n) + 1);
        grid = new boolean[n][n];
    }

    public void open(int row, int col) {
        validate(row, col);
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        int baseQFIndex = getQFIndex(row, col);
        if (row == 1) {
            weightedQuickUnionUF.union(baseQFIndex, virtualSiteTop);
            fullness.union(baseQFIndex, virtualSiteTop);
        }
        if (row == size) {
            weightedQuickUnionUF.union(baseQFIndex, virtualSiteBottom);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(baseQFIndex, getQFIndex(row - 1, col));
            fullness.union(baseQFIndex, getQFIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(baseQFIndex, getQFIndex(row + 1, col));
            fullness.union(baseQFIndex, getQFIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(baseQFIndex, getQFIndex(row, col - 1));
            fullness.union(baseQFIndex, getQFIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(baseQFIndex, getQFIndex(row, col + 1));
            fullness.union(baseQFIndex, getQFIndex(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return fullness.connected(getQFIndex(row, col), virtualSiteTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualSiteTop, virtualSiteBottom);
    }

    private int getQFIndex(int row, int col) {
        row = row - 1;
        col = col - 1;
        return (size * row) + col;
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("row and col should not be <=0 and not be > size");
    }
}
