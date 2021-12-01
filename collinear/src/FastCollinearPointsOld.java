import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPointsOld {

    //   private static ArrayList<Point[]> longChains = new ArrayList<Point[]>();
    private static Point[] tails;
    private static int tp = 0;

    private static void printn(Point[] points, int k, int v){

        Point[] ps = new Point[v+2];
        ps[0] = points[0];
        for(int i = 0; i <= v; i++) ps[i+1] = points[k + i]; //copy

        Arrays.sort(ps);
        int pl = ps.length;

/*
        for(Point[] longChain : longChains) {
           int cl = longChain.length;
            if (pl < cl) {
//                int c = 0; //coincidences
//                for (int i = 0; i < longChain.length; i++) {
//                    if (longChain[i] == ps[0] || longChain[i] == ps[1] )
//                        c++;
//                    if (c == 2) return; // already in
//                }
                if( ps[pl-1] == longChain[cl-1] && ps[pl-2] == longChain[cl-2] )
                    return;
            }
        }
*/
        for(int i = 0; i < tp; i += 2) {
            if( ps[pl-2] == tails[i] && ps[pl-1] == tails[i+1] )
                return;
        }


        if(v > 2) { // 5+ points
//            longChains.add(ps);
            tails[tp++] = ps[pl-2];
            tails[tp++] = ps[pl-1];
        }

        for(int i = 0; i < ps.length - 1; i++)
            StdOut.print(ps[i].toString() + " -> ");
        StdOut.println(ps[ps.length-1].toString());

        ps[0].drawTo(ps[ps.length-1]);
    }

    public static void main(String[] args) {
        //debug
        //Stopwatch sw = new Stopwatch();

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.setPenRadius(0.005); // make the points a bit larger

        // read in the input
        String filename = args[0];
        //String filename = "collinear\\equidistant.txt";
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        tails = new Point[2 * N * N];
        tp = 0;
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }

        Arrays.sort(points);

        StdDraw.setPenRadius();

        for (int i = 0; i < N-3; i++) {
            int sz = N - i;
            Point[] copy = new Point[sz];
            for (int j = 0; j < sz; j++) copy[j] = points[i + j]; //make a copy

            Arrays.sort(copy, 1, sz, copy[0].SLOPE_ORDER);

            for (int k = 1; k < sz-2;) {
                int v = 1;
                while( copy[0].slopeTo(copy[k]) == copy[0].slopeTo(copy[k+v]) ){
                    v++;
                    if ( (k+v) == copy.length) break;
                }
                v--;
                if (v >= 2) printn(copy, k, v);
                k = k + v + 1;
            }
        }

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();

        //debug
        // double time = sw.elapsedTime();
        // StdOut.println("time = " + time);

    } //main
}//class
