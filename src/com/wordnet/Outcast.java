package com.wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet)         // constructor takes a com.wordnet.WordNet object
    {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of com.wordnet.WordNet nouns, return an outcast
    {
        int length;
        int maxLength = -1;
        String bestNoun = null;

        for (String noun : nouns) {
            length = 0;
            for (String vertex : this.wordNet.nouns()) {
//                StdOut.println(vertex);
                length += this.wordNet.distance(noun, vertex);
            }
            if (maxLength == -1 || length > maxLength) {
                bestNoun = noun;
                maxLength = length;
            }
        }
        return bestNoun;
    }

    public static void main(String[] args)  // see test client below
    {
        String synsetPath = "E:\\文档\\IdeaProjects\\javaDataStructure\\input\\wordnet\\synsets50000-subgraph.txt";
        String hypernymPath = "E:\\文档\\IdeaProjects\\javaDataStructure\\input\\wordnet\\hypernyms50000-subgraph.txt";
        WordNet wordnet = new WordNet(synsetPath, hypernymPath);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 0; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}