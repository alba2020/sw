import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("point is null");
        }

        points = Arrays.copyOf(points, points.length);
        Arrays.sort(points);

        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0)
                throw new IllegalArgumentException("duplicate points");
        }

        ArrayList<LineSegment> list = new ArrayList<LineSegment>();
        ArrayList<Point> lasts = new ArrayList<Point>();

        int N = points.length;
        for (int i = 0; i < N; i++) {                     // 1
            Point pivot = points[i];
            Point last = null;
            for (int j = i + 1; j < N; j++) {             // 2
                double s1 = pivot.slopeTo(points[j]);

                for (int k = j + 1; k < N; k++) {         // 3
                    double s2 = pivot.slopeTo(points[k]);

                    if (s1 == s2) {
                        for (int l = k + 1; l < N; l++) { // 4
                            double s3 = pivot.slopeTo(points[l]);

                            if(s2 == s3) {
                                last = points[l];
                            }
                        }

                        if (last != null && (!lasts.contains(last)) ) {
                            list.add(new LineSegment(points[i], last));
                            lasts.add(last);
                        }
                    }
                }
            }
        }

        this.segments = list.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() { return this.segments.length; }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
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
