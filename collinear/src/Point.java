
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;
    private final int y;

    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        int x0 = this.x;
        int y0 = this.y;
        int x1 = that.x;
        int y1 = that.y;

        if (y0 < y1)
            return -1;
        else if (y0 == y1 && x0 < x1)
            return -1;
        else if (y0 == y1 && x0 == x1)
            return 0;

        return 1;
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        double one = 1.0; // for zeros

        double dy = that.y - this.y;
        double dx = that.x - this.x;

        if (dy == 0 && dx != 0) { // horizontal line
            return (one - one) / one; // positive zero ( 0.0)
        } else if (dy != 0 && dx == 0) { // vertical line
            return Double.POSITIVE_INFINITY;
        } else if (dy == 0 && dx == 0) { // degenerate == point
            return Double.NEGATIVE_INFINITY;
        }

        return dy / dx;
    }

    public Comparator<Point> slopeOrder() {
        return SLOPE_ORDER;
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);

            if (s1 < s2)
                return -1;
            else if (s1 > s2)
                return 1;

            return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
