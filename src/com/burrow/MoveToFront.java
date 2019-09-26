package com.burrow;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;


public class MoveToFront {
    private static int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < R; i++)
            list.add((char) i);
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = list.indexOf(c);
            list.remove(index);
            list.addFirst(c);
            BinaryStdOut.write((char) index);
        }
        BinaryStdOut.close();
    }


    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < R; i++)
            list.add((char) i);
        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readChar();
            char c = list.get(index);
            list.remove(index);
            list.addFirst(c);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            MoveToFront.encode();
        else if (args[0].equals("+"))
            MoveToFront.decode();
    }

}