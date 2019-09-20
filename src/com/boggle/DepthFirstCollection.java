package com.boggle;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SET;


public class DepthFirstCollection {
    private boolean[] marked;
    private Graph g;
    private char[] letters;
    private SET<String> collections;
    private TrieSET dictionary;

    public DepthFirstCollection(Graph g, char[] letters, TrieSET dictionary) {
        this.marked = new boolean[g.V()];
        this.g = g;
        this.letters = new char[letters.length];
        System.arraycopy(letters, 0, this.letters, 0, letters.length);
        this.collections = new SET<>();
        this.dictionary = dictionary;
    }

    public SET<String> getCollections() {
        for (int i = 0; i < g.V(); i++)
            dfs(i, new StringBuilder(), null);
        return collections;
    }

    private void dfs(int parent, StringBuilder current, Object node) {
        marked[parent] = true;
        int d = current.length();

        if (letters[parent] == 'Q')
            current.append("QU");
        else
            current.append(letters[parent]);

        int length = current.length();
        String string = current.toString();
        Object found = dictionary.keysWithPrefix(string, node, d);

        if (found == null) {
            marked[parent] = false;
            if (length - 2 >= 0 && string.substring(length - 2).equals("QU")) {
                current.deleteCharAt(length - 1);
                current.deleteCharAt(length - 2);
            } else
                current.deleteCharAt(length - 1);
            return;
        }

        if (length >= 3 && dictionary.contains(string))
            collections.add(string);

        for (int n : g.adj(parent))
            if (!marked[n])
                dfs(n, current, found);

        marked[parent] = false;
        if (length >= 2 && string.substring(length - 2).equals("QU")) {
            current.deleteCharAt(length - 1);
            current.deleteCharAt(length - 2);
        } else
            current.deleteCharAt(length - 1);
    }
}
