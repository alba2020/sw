import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int N;
    private int[][] blocks;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.N = blocks.length;

        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.blocks[i][j] = blocks[i][j];

    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                //if (i == N-1 && j == N-1) //last element=0
                //  break;
                if (blocks[i][j] == 0)
                    continue;
                else if (blocks[i][j] != i * N + j + 1)
                    h++;
            }
        return h;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                // if (i == N-1 && j == N-1) //last element=0
                // break;
                if (blocks[i][j] == 0)
                    continue;

                int b = blocks[i][j] - 1;

                int y = b / N;
                int x = b - y * N;
                int dy = Math.abs(y - i);
                int dx = Math.abs(x - j);
                m = m + dx + dy;

                // int u = blocks[y][x] - 1;
                // int uy = u / N;
                // int ux = u - uy * N;

                //if ((dx != 0 && y == i && j == ux ) || (dy != 0 && x == j && i == uy))
                //    m += 2;
            }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row

        Board twin = new Board(this.blocks);

        if (twin.blocks[0][0] != 0 && twin.blocks[0][1] != 0) { //swap on the 0th row
            twin.blocks[0][0] = this.blocks[0][1];
            twin.blocks[0][1] = this.blocks[0][0];
        } else { // swap in the 1st row
            twin.blocks[1][0] = this.blocks[1][1];
            twin.blocks[1][1] = this.blocks[1][0];
        }

        return twin;
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Stack<Board> n = new Stack<Board>();
        int i0 = 0, j0 = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    i0 = i; j0 = j;
                    break;
                }
            }
        // up
        if (i0 > 0) {
            Board bu = new Board(this.blocks);
            bu.blocks[i0 - 1][j0] = 0;
            bu.blocks[i0][j0] = this.blocks[i0 - 1][j0];
            n.push(bu);
        }

        // down
        if (i0 < N - 1) {
            Board bd = new Board(this.blocks);
            bd.blocks[i0 + 1][j0] = 0;
            bd.blocks[i0][j0] = this.blocks[i0 + 1][j0];
            n.push(bd);
        }

        // left
        if (j0 > 0) {
            Board bl = new Board(this.blocks);
            bl.blocks[i0][j0 - 1] = 0;
            bl.blocks[i0][j0] = this.blocks[i0][j0 - 1];
            n.push(bl);
        }

        // right
        if (j0 < N - 1) {
            Board br = new Board(this.blocks);
            br.blocks[i0][j0 + 1] = 0;
            br.blocks[i0][j0] = this.blocks[i0][j0 + 1];
            n.push(br);
        }

        return n;
    }

    public String toString() {
        // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", this.blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
