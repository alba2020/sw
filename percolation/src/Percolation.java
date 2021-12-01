import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] grid;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF flow;
    private int open;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Illegal percolation dimension.");

        this.n = n;
        this.grid = new boolean[n * n];
        for (int i = 0; i < n * n; i++) this.grid[i] = false;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.flow = new WeightedQuickUnionUF(n * n + 1);
        this.open = 0;
    }

    private int offset(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Coordinates out of bounds.");
    }

    // opens the site (row, col) if it is not open already
    // row, col - 1 .. n
    public void open(int row, int col) {
        validate(row, col);

        final int pos = offset(row, col);

        if (grid[pos]) return; // already open
        else grid[pos] = true;

        if (row == 1) {
            uf.union(pos, n * n); // connect to top
            flow.union(pos, n * n); // connect to top view
        }
        if (row == n) {
            uf.union(pos, n * n + 1); // connect to bottom
        }

        if ((row + 1 <= n) && isOpen(row + 1, col)) {
            uf.union(pos, offset(row + 1, col));
            flow.union(pos, offset(row + 1, col));
        }
        if ((col + 1 <= n) && isOpen(row, col + 1)) {
            uf.union(pos, offset(row, col + 1));
            flow.union(pos, offset(row, col + 1));
        }
        if ((row - 1 >= 1) && isOpen(row - 1, col)) {
            uf.union(pos, offset(row - 1, col));
            flow.union(pos, offset(row - 1, col));
        }
        if ((col - 1 >= 1) && isOpen(row, col - 1)) {
            uf.union(pos, offset(row, col - 1));
            flow.union(pos, offset(row, col - 1));
        }

        open++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[offset(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return flow.find(offset(row, col)) == flow.find(n * n); // connected to top?
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(n * n) == // top
                uf.find(n * n + 1);  // to bottom
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 4;
        Percolation p = new Percolation(n);

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                System.out.println(p.isOpen(i, j));

        System.out.println("-----------");

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                boolean f = p.isFull(i, j);
                if (f)
                    System.out.println(i + " " + j);
            }
        }
    }
}
