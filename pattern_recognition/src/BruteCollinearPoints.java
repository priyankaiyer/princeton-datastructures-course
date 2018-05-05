import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Constructor argument cannot be null");
        }
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        Point[] anotherArray = Arrays.copyOf(points, points.length);
        checkPoints(anotherArray);
        for (int i = 0; i < anotherArray.length; i++) {
            for (int a = i + 1; a < anotherArray.length; a++) {
                for (int b = a + 1; b < anotherArray.length; b++) {
                    for (int c = b + 1; c < anotherArray.length; c++) {
                        Point p = anotherArray[i], q = anotherArray[a], r = anotherArray[b], s = anotherArray[c];
                        if (Double.compare(p.slopeTo(q), q.slopeTo(r)) == 0 && Double.compare(q.slopeTo(r), r.slopeTo(s)) == 0) {
                            lineSegmentArrayList.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }

        lineSegments = lineSegmentArrayList.toArray(new LineSegment[lineSegmentArrayList.size()]);
    }

    private void checkPoints(Point[] points) {
        for (Point point : points) {
            if (null == point)
                throw new IllegalArgumentException("Point cannot be null");
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("No two points can be the same");
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        LineSegment[] copiedLineSegments = Arrays.copyOf(this.lineSegments, this.lineSegments.length);
        return copiedLineSegments;
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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment.toString());
        }
    }
}
