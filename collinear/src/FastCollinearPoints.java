import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        ArrayList<LineSegment> list = new ArrayList<LineSegment>();
        Point[] ps = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(ps);
            Arrays.sort(ps, points[i].slopeOrder());

            System.out.println("=== here is the copy sorted for " + points[i]);
            for (int t = 0; t < ps.length; t++) {
                double slope = points[i].slopeTo(ps[t]);
                System.out.print(ps[t] + "[" + slope + "] ");
            }
            System.out.println();

            for (int j = 0; j < ps.length; j++) {
                double slope = points[i].slopeTo(ps[j]);
                System.out.println("slope " + points[i] + " -> " + ps[j] + " = " + slope);
                int n = 2;

                while(j < ps.length - 1 && points[i].slopeTo(ps[j + 1]) == slope) {
                    double sl = points[i].slopeTo(ps[j+1]);
                    System.out.println("slope " + points[i] + " -> " + ps[j + 1] + " = " + sl +
                            " n = " + (n + 1));
                    j++;
                    n++;
                }

                if (n > 3) { // ???
                    System.out.println("add segment <" + points[i] + ", " + ps[j] + ">");
                    list.add(new LineSegment(points[i], ps[j]));
                }
            }
            System.out.println("=== next");
        }
        this.segments = list.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
