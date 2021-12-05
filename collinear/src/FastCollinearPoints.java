import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private final ArrayList<LineSegment> segList;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points is null");
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("point is null");
        }

        Point[] ps = Arrays.copyOf(points, points.length);
        Arrays.sort(ps);

        for (int i = 1; i < ps.length; i++) {
            if (ps[i].compareTo(ps[i-1]) == 0)
                throw new IllegalArgumentException("duplicate points");
        }

        // -------------------------------------
        segList = new ArrayList<LineSegment>();

        ArrayList<Point> current = new ArrayList<Point>();

        for (Point pivot : points) {
            Arrays.sort(ps, pivot.slopeOrder());

//            System.out.println("=== here is the copy sorted for " + points[i]);
//            for (int t = 0; t < ps.length; t++) {
//                double slope = points[i].slopeTo(ps[t]);
//                System.out.print(ps[t] + "[" + slope + "] ");
//            }
//            System.out.println();

            for (int j = 0; j < ps.length; j++) {
                double slope = pivot.slopeTo(ps[j]);
//                System.out.println("slope " + points[i] + " -> " + ps[j] + " = " + slope);

                current.clear();
                Point saved = ps[j];

                while (j < ps.length - 1 && pivot.slopeTo(ps[j + 1]) == slope) {
                    current.add(ps[j+1]); // ps[j+1] is collinear
                    j++;
                }

                if (current.size() >= 2) {
                    current.add(pivot);
                    current.add(saved); // add first point

//                    System.out.println("*** current points");
//                    for (int c = 0; c < current.size(); c++) {
//                        System.out.println(current.get(c) + " ");
//                    }

//                    Collections.sort(current);
//                    if (current.indexOf(pivot) == 0) {

                    if (Collections.min(current) == pivot) {
//                        Point last = current.get(current.size() - 1);
                        Point last = Collections.max(current);
                        segList.add(new LineSegment(pivot, last));
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segList.size();
    }

    public LineSegment[] segments() {
        return segList.toArray(new LineSegment[0]);
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
