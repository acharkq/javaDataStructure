import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private int len;
    private LinkedList<LineSegment> lineSegment;

    public BruteCollinearPoints(Point[] pointsO) {
        if (pointsO == null)
            throw new IllegalArgumentException();
        for (Point p : pointsO)
            if (p == null)
                throw new IllegalArgumentException();
        Point[] points = new Point[pointsO.length];
        System.arraycopy(pointsO, 0, points, 0, pointsO.length);
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        len = 0;
        lineSegment = new LinkedList<>();
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = j + 1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k]))
                        continue;
                    for (int l = k + 1; l < points.length; l++)
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            lineSegment.addLast(new LineSegment(points[i], points[l]));
                            len++;
                        }
                }
    }

    public LineSegment[] segments() {
        LineSegment[] tmp = new LineSegment[len];
        return lineSegment.toArray(tmp);
    }

    public int numberOfSegments() {
        return len;
    }
}
