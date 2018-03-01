import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private int len;
    private LinkedList<LineSegment> segments;

    public FastCollinearPoints(Point[] pointsO) {
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
        segments = new LinkedList<>();
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points, i + 1, points.length, points[i].slopeOrder());
            for (int j = i + 1; j < points.length - 2; j++) {
                int span = 1;
                while (j < points.length - 1) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[j + 1]))
                        break;
                    span++;
                    j++;
                }
                if (span >= 3) {
                    boolean exit = false;
                    for (int k = 0; k < i; k++)
                        if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[j])) {
                            exit = true;
                            break;
                        }
                    if (exit)
                        continue;
                    segments.addLast(new LineSegment(points[i], points[j]));
                    len++;
                }
            }
            Arrays.sort(points, i + 1, points.length);
        }
    }

    public int numberOfSegments() {
        return len;
    }

    public LineSegment[] segments() {
        LineSegment[] tmp = new LineSegment[len];
        return segments.toArray(tmp);
    }

}
