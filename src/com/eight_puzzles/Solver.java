package com.eight_puzzles;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode currentNode;

    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        SearchNode init = new SearchNode(initial, null);
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(init);

        SearchNode twinCurrentNode;
        SearchNode twinInit = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
        twinMinPQ.insert(twinInit);

        while (true) {
            currentNode = minPQ.delMin();
            if (currentNode.board.isGoal())
                break;
            for (Board neighbor : currentNode.board.neighbors())
                if (currentNode.preSearchNode == null || !neighbor.equals(currentNode.preSearchNode.board))
                    minPQ.insert(new SearchNode(neighbor, currentNode));

            twinCurrentNode = twinMinPQ.delMin();
            if (twinCurrentNode.board.isGoal())
                break;
            for (Board neighbor : twinCurrentNode.board.neighbors())
                if (twinCurrentNode.preSearchNode == null || !neighbor.equals(twinCurrentNode.preSearchNode.board))
                    twinMinPQ.insert(new SearchNode(neighbor, twinCurrentNode));
        }
    }

    public boolean isSolvable() {
        return currentNode.board.isGoal();
    }

    public int moves() {
        if (isSolvable())
            return currentNode.moves;
        else
            return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> solution = new Stack<>();
            SearchNode node = currentNode;
            while (node != null) {
                solution.push(node.board);
                node = node.preSearchNode;
            }
            return solution;
        } else
            return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        SearchNode preSearchNode;
        int moves;
        final int priority;

        SearchNode(Board inBoard, SearchNode preSearchNode) {
            board = inBoard;
            this.preSearchNode = preSearchNode;
            if (preSearchNode == null)
                moves = 0;
            else
                moves = preSearchNode.moves + 1;
            priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        StdOut.println(initial);
        if (solver.isSolvable()) {
            for (Board a : solver.solution())
                StdOut.println(a);
//            StdOut.println(solver.solution().pop());
            StdOut.println(solver.moves());
        } else {
            StdOut.println("it is not solvable");
        }
    }
}
