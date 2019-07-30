package com.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
    private int size;
    private Node root;

    public KdTree() {
        size = 0;
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, p, true, null);
    }

    private Node insert(Node current, Point2D kid, boolean orientation, Node parent) {
//        orientation: true for x coordinate, false for y coordinate; start from x coordinate
        if (current == null) {
            size++;
            if (parent == null) return new Node(kid, new RectHV(0, 0, 1, 1));
            Point2D p = parent.p;
            orientation = !orientation;
            int cmp = compareTo(kid, p, orientation);
            RectHV newSpace, space = parent.rect;

            if (cmp >= 0) {
                if (orientation) newSpace = new RectHV(p.x(), space.ymin(), space.xmax(), space.ymax());
                else newSpace = new RectHV(space.xmin(), p.y(), space.xmax(), space.ymax());
            } else {
                if (orientation) newSpace = new RectHV(space.xmin(), space.ymin(), p.x(), space.ymax());
                else newSpace = new RectHV(space.xmin(), space.ymin(), space.xmax(), p.y());
            }
            return new Node(kid, newSpace);
        }

        int cmp = compareTo(kid, current.p, orientation);
        if (cmp == 0) {
            int cmp2 = compareTo(kid, current.p, !orientation);
            if (cmp2 == 0) return current;
        }
        if (cmp >= 0) current.rt = insert(current.rt, kid, !orientation, current);
        else current.lb = insert(current.lb, kid, !orientation, current);
        return current;
    }

    private int compareTo(Point2D a, Point2D b, boolean orientation) {
//        orientation: true for x coordinate, false for y coordinate; start from x coordinate
        double aCor, bCor;
        if (orientation) {
            aCor = a.x();
            bCor = b.x();
        } else {
            aCor = a.y();
            bCor = b.y();
        }
        if (aCor > bCor) return 1;
        else if (aCor < bCor) return -1;
        return 0;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    private boolean contains(Node parent, Point2D kid, boolean orientation) {
        if (parent == null) return false;
        int cmp = compareTo(kid, parent.p, orientation);
        if (cmp == 0) {
            int cmp2 = compareTo(kid, parent.p, !orientation);
            if (cmp2 == 0) return true;
        }
        if (cmp >= 0) return contains(parent.rt, kid, !orientation);
        else return contains(parent.lb, kid, !orientation);
    }

    public void draw() {
        draw(root, true);
        StdDraw.show();
    }

    private void draw(Node node, boolean orientation) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.p.x(), node.p.y());
        if (orientation) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        if (node.lb != null) draw(node.lb, !orientation);
        if (node.rt != null) draw(node.rt, !orientation);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(root, p, root.p, true, false);
    }

    private Point2D nearest(Node parent, Point2D p, Point2D bestPoint, boolean orientation, boolean out) {
        if (parent == null) return bestPoint;
        if (out && parent.rect.distanceSquaredTo(p) > bestPoint.distanceSquaredTo(p)) return bestPoint;
        if (parent.p.distanceSquaredTo(p) < bestPoint.distanceSquaredTo(p)) bestPoint = parent.p;
        if (parent.rt == null && parent.lb == null) return bestPoint;

        int cmp = compareTo(p, parent.p, orientation);
        if (cmp >= 0) {
            bestPoint = nearest(parent.rt, p, bestPoint, !orientation, false);
            bestPoint = nearest(parent.lb, p, bestPoint, !orientation, true);
        } else {
            bestPoint = nearest(parent.lb, p, bestPoint, !orientation, false);
            bestPoint = nearest(parent.rt, p, bestPoint, !orientation, true);
        }
        return bestPoint;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Stack<Point2D> point2DStack = new Stack<>();
        iterateTree(root, rect, point2DStack, true);
        return point2DStack;
    }

    private void iterateTree(Node parent, RectHV rect, Stack<Point2D> point2DStack, boolean orientation) {
        if (parent == null) return;
        Point2D parentPoint = parent.p;
        if (rect.contains(parentPoint)) point2DStack.push(parent.p);
        boolean intersect = false;
        if (orientation && rect.xmin() <= parentPoint.x() && rect.xmax() >= parentPoint.x()) intersect = true;
        else if (!orientation && rect.ymin() <= parentPoint.y() && rect.ymax() >= parentPoint.y()) intersect = true;
        if (intersect) {
            iterateTree(parent.lb, rect, point2DStack, !orientation);
            iterateTree(parent.rt, rect, point2DStack, !orientation);
        } else {
            if ((orientation && rect.xmin() > parentPoint.x()) || (!orientation && rect.ymin() > parentPoint.y()))
                iterateTree(parent.rt, rect, point2DStack, !orientation);
            else
                iterateTree(parent.lb, rect, point2DStack, !orientation);
        }
    }

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node lb; // left/bottom subtree
        private Node rt; // right/top subtree

        Node(Point2D p, RectHV space) {
            this.p = p;
            this.lb = null;
            this.rt = null;
            rect = space;
        }
    }
}
