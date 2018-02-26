import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final private int n;
    private int openNumber;
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private boolean[] gridStatus;

    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        int i;
        this.n = n;
        openNumber = 0;
        gridStatus = new boolean[n * n + 1];
        uf1 = new WeightedQuickUnionUF(n * n + 1);
        uf2 = new WeightedQuickUnionUF(n * n + 2);
        gridStatus[0] = true;
        for (i = 1; i <= n * n; i++)
            gridStatus[i] = false;
        for (i = 1; i <= n; i++) {
            uf1.union(i, 0);
            uf2.union(i, 0);
            uf2.union(n * n + 1 - i, n * n + 1);
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            System.out.println("1");
            throw new java.lang.IllegalArgumentException();
        }
        int pos = (row - 1) * n + col;
        if (!isOpen(row, col)) {
            openNumber++;
            gridStatus[pos] = true;
            if (col != 1)
                if (gridStatus[pos - 1]) {
                    uf1.union(pos, pos - 1);
                    uf2.union(pos, pos - 1);
                }
            if (col != n)
                if (gridStatus[pos + 1]) {
                    uf1.union(pos, pos + 1);
                    uf2.union(pos, pos + 1);
                }
            if (row != 1)
                if (gridStatus[pos - n]) {
                    uf1.union(pos, pos - n);
                    uf2.union(pos, pos - n);
                }
            if (row != n)
                if (gridStatus[pos + n]) {
                    uf1.union(pos, pos + n);
                    uf2.union(pos, pos + n);
                }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new java.lang.IllegalArgumentException();
        return gridStatus[(row - 1) * n + col];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new java.lang.IllegalArgumentException();
        return uf1.connected((row - 1) * n + col, 0) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openNumber;
    }

    public boolean percolates() {
        if (n != 1)
            return uf2.connected(0, n * n + 1);
        return isOpen(1, 1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);

//        p.open(2, 1);
//        p.open(3, 1);
        System.out.println(p.percolates());
    }
}
