import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node solution = null;
    private boolean solvable = false;

    private class Node implements Comparable<Node>{
        private Board board;
        private int steps = 0;
        private Node parent;
        private int manhattan; // cache

        public Node(Board board, int steps, Node parent) {
            this.board = board;
            this.steps = steps;
            this.parent = parent;
            this.manhattan = board.manhattan();
        }

        public int compareTo(Node that) {
            if (this.priority() < that.priority()) return -1;
            else if (this.priority() > that.priority()) return 1;
            else return 0;
        }

        public int priority() {
            return this.manhattan + this.steps;
        }

        public Iterable<Node> children() {
            Stack<Node> n = new Stack<Node>();
            for(Board b : this.board.neighbors()) {
                if (parent != null && b.equals(parent.board)) continue; // NOT != !!!!!
                n.push(new Node(b, steps + 1, this));
            }
            return n;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial board is null");
        }
        // find a solution to the initial board (using the A* algorithm)
        MinPQ<Node> fringe = new MinPQ<Node>();
        fringe.insert(new Node(initial, 0, null));

        MinPQ<Node> antifringe = new MinPQ<Node>();
        antifringe.insert(new Node(initial.twin(), 0, null));

        while(true) {
            Node node = fringe.delMin();
            Node antinode = antifringe.delMin();
            if(node.board.isGoal()) {
                solution = node;
                solvable = true;
                break;
            } else if (antinode.board.isGoal()) {
                solution = null;
                solvable = false;
                break;
            } else {
                for (Node ch : node.children()) fringe.insert(ch);
                for (Node ach : antinode.children()) antifringe.insert(ach);
            }
        }
    } // constructor

    public boolean isSolvable() {
        // is the initial board solvable?
        return this.solvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (!isSolvable()) return -1;
        return solution.steps;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if no solution
        if (!isSolvable()) return null;
        Node n = solution;
        Stack<Board> boards = new Stack<Board>();
        while(n != null) {
            boards.push(n.board);
            n = n.parent;
        }
        return boards;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        //In in = new In("8p\\puzzle04.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    } //main
} //class