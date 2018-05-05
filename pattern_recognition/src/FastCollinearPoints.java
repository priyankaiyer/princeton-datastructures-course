import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Constructor argument cannot be null");
        }
        Point[] anotherArray = Arrays.copyOf(points, points.length);
        checkPoints(anotherArray);
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        Point[] auxillaryArray = Arrays.copyOf(points, points.length);
        for (Point point : anotherArray) {
            Arrays.sort(auxillaryArray, point.slopeOrder().thenComparing(Comparator.naturalOrder()));
            double previousSlope = 0.0;
            Point previousPoint = point;
            Point origin = null;
            ArrayList<Point> collinearPoints = new ArrayList<>();
            for (Point point1 : auxillaryArray) {
                double currentSlope = point.slopeTo(point1);
                if (Double.compare(previousSlope, currentSlope) == 0) {
                    if (collinearPoints.size() == 0) {
                        collinearPoints.add(point);
                        origin = previousPoint;
                    }
                    collinearPoints.add(previousPoint);
                    collinearPoints.add(point1);
                } else {
                    if (collinearPoints.size() >= 4) {
                        if (origin != null && point.compareTo(origin) < 0) {
                            lineSegmentArrayList.add(new LineSegment(point, collinearPoints.get(collinearPoints.size() - 1)));
                        }
                    }
                    collinearPoints.clear();
                }
                if (point1 == auxillaryArray[auxillaryArray.length - 1]) {
                    if (collinearPoints.size() >= 4 && !collinearPoints.isEmpty() && origin != null && point.compareTo(origin) < 0) {
                        lineSegmentArrayList.add(new LineSegment(point, collinearPoints.get(collinearPoints.size() - 1)));
                    }
                    collinearPoints.clear();
                }
                previousSlope = currentSlope;
                previousPoint = point1;
            }
        }

        lineSegments = lineSegmentArrayList.toArray(new LineSegment[lineSegmentArrayList.size()]);
    }

    private void checkPoints(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Point cannot be null");
            }
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (i != points.length - 1 && points[i].compareTo(points[i + 1]) == 0) {
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment.toString());
        }
    }

}
