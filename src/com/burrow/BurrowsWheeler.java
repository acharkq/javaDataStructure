package com.burrow;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String in = BinaryStdIn.readString();
        int length = in.length();
        CircularSuffixArray csa = new CircularSuffixArray(in);
        for (int i = 0; i < length; i++)
            if (csa.index(i) == 0)
                BinaryStdOut.write(i);
        for (int i = 0; i < length; i++) {
            char c = in.charAt((csa.index(i) + length - 1) % length);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        // Read
        int first = BinaryStdIn.readInt();
        char[] encoded = BinaryStdIn.readString().toCharArray();
        // Declaration and clone
        int R = 256;
        int length = encoded.length;
        // key-index sorting
        int[] keyCount = new int[R + 1];
        for (int i : encoded)
            keyCount[i + 1]++;
        for (int i = 0; i < R; i++)
            keyCount[i + 1] += keyCount[i];
        int[] next = new int[length];
        for (int i = 0; i < length; i++)
            next[keyCount[encoded[i]]++] = i;
        Arrays.sort(encoded);
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(encoded[first]);
            first = next[first];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-"))
            BurrowsWheeler.transform();
        else if (args[0].equals("+"))
            BurrowsWheeler.inverseTransform();
    }
}