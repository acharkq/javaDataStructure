package com.boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Graph;


public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TrieSET dictionary;


    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TrieSET();
        for (String word : dictionary)
            this.dictionary.add(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        int V = rows * cols;

        Graph g = new Graph(V);
        for (int i = 1; i <= V; i++) {
            int pos = i - 1;
            if (i % cols != 0)
                g.addEdge(pos + 1, pos);
            if (i + cols <= V) {
                g.addEdge(pos + cols, pos);
                if (i % cols != 0)
                    g.addEdge(pos + cols + 1, pos);
            }
            if (i > cols && i % cols != 0)
                g.addEdge(pos - cols + 1, pos);
        }

        char[] letters = new char[V];
        for (int i = 0; i < V; i++)
            letters[i] = board.getLetter(i / cols, i % cols);

        DepthFirstCollection dfc = new DepthFirstCollection(g, letters, dictionary);
        return dfc.getCollections();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionary.contains(word))
            return 0;
        int length = word.length();
        if (length < 3)
            return 0;
        if (length <= 4)
            return 1;
        if (length <= 5)
            return 2;
        if (length <= 6)
            return 3;
        if (length <= 7)
            return 5;
        return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        StdOut.print(board.toString());
        StdOut.println();
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
