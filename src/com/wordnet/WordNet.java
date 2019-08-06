package com.wordnet;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;


public class WordNet {

    private int v;
    private SAP sap;
    private HashMap<String, Stack<Integer>> noun2Ids;
    private HashMap<Integer, String> id2Synsets;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        synsetsConstructor(synsets);
        hypernymsConstructor(hypernyms);
    }

    private void synsetsConstructor(String synsetsPath) {
        In in = new In(synsetsPath);
        this.noun2Ids = new HashMap<>();
        this.id2Synsets = new HashMap<>();
        String synsets;
        String[] nouns;

        Stack<Integer> ids;

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            synsets = fields[1];
            nouns = synsets.split(" ");
            int id = Integer.parseInt(fields[0]);
            this.id2Synsets.put(id, synsets);
            for (String noun : nouns) {

                if (this.noun2Ids.containsKey(noun)) {
                    ids = this.noun2Ids.get(noun);
                    ids.push(id);
                } else {
                    ids = new Stack<>();
                    ids.push(id);
                    this.noun2Ids.put(noun, ids);
                }
            }

        }
        this.v = this.id2Synsets.size();
    }

    private void hypernymsConstructor(String hypernymPath) {
        In in = new In(hypernymPath);
        Digraph g = new Digraph(this.v);

        String line;
        String[] fields;
        int current, parent;
        while (!in.isEmpty()) {
            line = in.readLine();
            fields = line.split(",");
            if (fields.length > 1) {
                current = Integer.parseInt(fields[0]);
                for (int i = 1; i < fields.length; i++) {
                    parent = Integer.parseInt(fields[i]);
                    g.addEdge(current, parent);
                }
            }
        }

        // check whether have a rooted vertex
        int rootNum = 0;
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0) {
                rootNum ++;
            }
        }
        if (rootNum != 1) throw new IllegalArgumentException();
        if (new DirectedCycle(g).hasCycle()) throw new IllegalArgumentException();
        this.sap = new SAP(g);
    }

    // returns all com.wordnet.WordNet nouns
    public Iterable<String> nouns() {
        return this.noun2Ids.keySet();
    }

    // is the word a com.wordnet.WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return this.noun2Ids.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!this.noun2Ids.containsKey(nounA) || !this.noun2Ids.containsKey(nounB))
            throw new IllegalArgumentException();
        Stack<Integer> idsA = this.noun2Ids.get(nounA), idsB = this.noun2Ids.get(nounB);
        return this.sap.length(idsA, idsB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!this.noun2Ids.containsKey(nounA) || !this.noun2Ids.containsKey(nounB))
            throw new IllegalArgumentException();
        Stack<Integer> idsA = this.noun2Ids.get(nounA), idsB = this.noun2Ids.get(nounB);
        int ancestorId = this.sap.ancestor(idsA, idsB);
        return this.id2Synsets.get(ancestorId);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsetPath = "E:\\文档\\IdeaProjects\\javaDataStructure\\input\\wordnet\\synsets.txt";
        String hypernymPath = "E:\\文档\\IdeaProjects\\javaDataStructure\\input\\wordnet\\hypernyms.txt";
        WordNet wn = new WordNet(synsetPath, hypernymPath);
        StdOut.println(wn.distance("Sir_Patrick_Manson", "operant_conditioning"));
    }
}