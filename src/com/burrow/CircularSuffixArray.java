package com.burrow;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {

    private int length;
    private Integer[] indices;
    private String s;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException();
        this.s = s;
        this.length = s.length();
        indices = new Integer[length];
        for (int i = 0; i < length; i++)
            indices[i] = i;
        Arrays.sort(indices, new CircularSuffixComparator());
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length)
            throw new IllegalArgumentException();
        return indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray aa = new CircularSuffixArray(args[0]);
        System.out.println(aa.length());
        for (int i = 0; i < aa.length; i++)
            System.out.println(aa.index(i));
    }

    private class CircularSuffixComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            int k = 0;
            char c1, c2;
            while (k < length) {
                c1 = s.charAt((a + k) % length);
                c2 = s.charAt((b + k) % length);
                if (c1 != c2)
                    return c1 - c2;
                k++;
            }
            return 0;
        }
    }
}