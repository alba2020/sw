import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("points is null");

        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("point is null");
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("duplicate points");
        }

        ArrayList<LineSegment> list = new ArrayList<LineSegment>();

        int N = points.length;
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < N; k++)
                    for (int l = k + 1; l < N; l++) {
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[j].slopeTo(points[k]);
                        double s3 = points[k].slopeTo(points[l]);

                        if(s1 == s2 && s2 == s3) {
                            list.add(new LineSegment(points[i], points[l]));
                            print4(points[i], points[j], points[k], points[l]);
                        }

//                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
//                            points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
//                            print4(points[i], points[j], points[k], points[l]);
//                            System.out.println(points[i] + " " + points[l]);
//                        }
                    }

        this.segments = list.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return this.segments.length;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    private static void print4(Point p, Point q, Point r, Point s) {
        Point[] ps = {p, q, r, s};
        Arrays.sort(ps);
        StdOut.println(ps[0].toString() + " -> " + ps[1].toString() + " -> " +
                ps[2].toString() + " -> " + ps[3].toString() );

        ps[0].drawTo(ps[3]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

} //class
