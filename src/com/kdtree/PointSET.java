package com.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RedBlackBST;


public class PointSET {
    private final RedBlackBST<Point2D, Integer> points;

    public PointSET() {
        points = new RedBlackBST<>();
    }

    public boolean isEmpty() {
        return this.points.isEmpty();
    }

    public int size() {
        return this.points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        this.points.put(p, 1);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);

        for (Point2D point : points.keys())
            StdDraw.point(point.x(), point.y());
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Stack<Point2D> point2DStack = new Stack<>();
        for (Point2D point : points.keys())
            if (rect.contains(point))
                point2DStack.push(point);
        return point2DStack;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return null;
        Point2D neareastPoint = null;
        for (Point2D point : points.keys()) {
            if (neareastPoint == null)
                neareastPoint = point;
            else if (p.distanceSquaredTo(neareastPoint) > p.distanceSquaredTo(point))
                neareastPoint = point;
        }
        return neareastPoint;
    }

    public static void main(String[] args) {
        PointSET test = new PointSET();
        StdOut.println(test.isEmpty());
        Point2D a = new Point2D(0.1, 0.2);
        Point2D b = new Point2D(0.2, 0.2);
        Point2D c = new Point2D(3, 4);
        test.insert(a);
        test.insert(b);
        StdOut.println(test.size());
        StdOut.println(test.contains(a));
        StdOut.println(test.nearest(c));
        test.draw();
    }
}
