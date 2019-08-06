package com.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class SAP {
    private DeluxeBreadthFirstSearch vBFS;
    private DeluxeBreadthFirstSearch wBFS;
    private int v;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        this.v = G.V();
        this.vBFS = new DeluxeBreadthFirstSearch(G);
        this.wBFS = new DeluxeBreadthFirstSearch(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        Stack<Integer> vStack = new Stack<>(), wStack = new Stack<>();
        vStack.push(v);
        wStack.push(w);
        int[] result = findAnscestor(vStack, wStack);
        return result[0];
    }

    private void checkIterable(Iterable<Integer> v) {
        for (Integer item : v) {
            if (item == null || item >= this.v || item < 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private int[] findAnscestor(Iterable<Integer> v, Iterable<Integer> w) {

        checkIterable(v);
        checkIterable(w);

        this.vBFS.init(v);
        this.wBFS.init(w);

        int minLength = -1, minAncestor = -1;
        int vLength, wLength;

        for (int i = 0; i < this.v; i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                vLength = vBFS.distTo(i);
                wLength = wBFS.distTo(i);
                if (minLength == -1 || vLength + wLength < minLength) {
                    minLength = vLength + wLength;
                    minAncestor = i;
                }
            }
        }
        return new int[]{minLength, minAncestor};
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        Stack<Integer> vStack = new Stack<>(), wStack = new Stack<>();
        vStack.push(v);
        wStack.push(w);
        int[] result = findAnscestor(vStack, wStack);
        return result[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();

        int[] result = findAnscestor(v, w);
        return result[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        int[] result = findAnscestor(v, w);
        return result[1];
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
