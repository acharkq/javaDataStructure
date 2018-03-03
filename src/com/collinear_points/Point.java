package com.collinear_points;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x, y;

    public Comparator<Point> slopeOrder() {
        return new slopeOrder();
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y != that.y)
            return this.y > that.y ? 1 : -1;
        else if (this.x != that.x)
            return this.x > that.x ? 1 : -1;
        else
            return 0;
    }

    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y)
            return +0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    private class slopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double r = slopeTo(p1) - slopeTo(p2);
            if (r > 0)
                return 1;
            else if (r < 0)
                return -1;
            else return 0;
        }
    }
}
