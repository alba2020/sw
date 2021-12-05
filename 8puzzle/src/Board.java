import edu.princeton.cs.algs4.Stack;

public class Board {

    private int N;
    private char[][] blocks;

    private int manhattan = -1;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.N = blocks.length;

        this.blocks = new char[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.blocks[i][j] = (char) blocks[i][j];

    }

    private Board() { }

    private static Board createBoard(char[][] blocks){
        Board b = new Board();
        int N = blocks.length;
        b.N = N;
        b.blocks = new char[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                b.blocks[i][j] = blocks[i][j];
        return b;
    }

    public int dimension() {
        // board dimension N
        return N;
    }

    public int hamming() {
        // number of blocks out of place
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

    private int computeManhattan() {
        // sum of Manhattan distances between blocks and goal
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

    public int manhattan() {
        if (this.manhattan == -1)
            this.manhattan = computeManhattan();
        return this.manhattan;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row

        Board twin = createBoard(this.blocks);

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
            Board bu = createBoard(this.blocks);
            bu.blocks[i0 - 1][j0] = 0;
            bu.blocks[i0][j0] = this.blocks[i0 - 1][j0];
            n.push(bu);
        }

        // down
        if (i0 < N - 1) {
            Board bd = createBoard(this.blocks);
            bd.blocks[i0 + 1][j0] = 0;
            bd.blocks[i0][j0] = this.blocks[i0 + 1][j0];
            n.push(bd);
        }

        // left
        if (j0 > 0) {
            Board bl = createBoard(this.blocks);
            bl.blocks[i0][j0 - 1] = 0;
            bl.blocks[i0][j0] = this.blocks[i0][j0 - 1];
            n.push(bl);
        }

        // right
        if (j0 < N - 1) {
            Board br = createBoard(this.blocks);
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
                s.append(String.format("%2d ", (int)this.blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
